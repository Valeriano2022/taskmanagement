package com.example.taskapi.service

import com.example.taskapi.dto.auth.*
import com.example.taskapi.dto.user.UserResponse
import com.example.taskapi.exception.EmailAlreadyExistsException
import com.example.taskapi.exception.InvalidRequestException
import com.example.taskapi.exception.TokenNotFound
import com.example.taskapi.exception.UnauthorizedOperationException
import com.example.taskapi.exception.UserAlreadyLoggedIn
import com.example.taskapi.exception.UserNotFoundException
import com.example.taskapi.model.RefreshToken
import com.example.taskapi.model.User
import com.example.taskapi.repository.RefreshTokenRepository
import com.example.taskapi.repository.UserRepository
import com.example.taskapi.security.JwtUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,
    private val tokenRepository: RefreshTokenRepository,
    private val jwtUtil: JwtUtil
) {
    @Transactional
    fun signup(request: SignupRequest) {
        val email = request.email.trim().lowercase()

        if (userRepository.existsByEmail(email))
            throw EmailAlreadyExistsException(email)

        val user = User(
            email = email,
            password = passwordEncoder.encode(request.password)
                ?: throw InvalidRequestException("Password is required!")
        )

        userRepository.save(user)
    }

    @Transactional
    fun login(request: LoginRequest, response: HttpServletResponse): LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException("User not found")

        if (!passwordEncoder.matches(request.password, user.password))
            throw BadCredentialsException("Invalid credentials")

        val activeToken = tokenRepository.findAllByUserAndExpiredIsFalseAndRevokedIsFalse(user)

        if (activeToken.isNotEmpty())
            throw UserAlreadyLoggedIn(user.id!!)

        val refreshToken = refreshTokenService.createRefreshToken(user)

        val accessToken = jwtUtil.generateAccessToken(user.id!!, user.email)

        val refreshCookie = Cookie("refreshToken", refreshToken.token).apply {
            isHttpOnly = true
            secure = false
            path = "/"
            maxAge = 7 * 24 * 60 * 60
            setAttribute("SameSite", "Lax")
        }

        response.addCookie(refreshCookie)
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken.token,
            user = UserResponse(
                id = user.id!!,
                email = user.email
            ))
    }

    fun refresh(request: RefreshTokenRequest): RefreshResponse {
        val oldToken = request.refreshToken

        val validated = refreshTokenService.validateToken(oldToken)

        val newRefreshToken = refreshTokenService.rotateToken(oldToken)

        val user = validated.user

        return RefreshResponse(
            accessToken = jwtUtil.generateAccessToken(user.id!!, user.email),
            refreshToken = newRefreshToken.token,
            user = UserResponse(user.id!!, user.email)
        )
    }
    @Transactional
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {

        val token = request.cookies?.firstOrNull { it.name == "refreshToken" }?.value

        println(token)

        val storedToken = refreshTokenService.findByToken(token?: throw TokenNotFound())
            ?: throw TokenNotFound()
        storedToken.revoked = true
        storedToken.expired = true

        refreshTokenService.saveToken(storedToken)

        SecurityContextHolder.clearContext()

        val cookie = Cookie("refreshToken", null)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = false
        cookie.maxAge = 0
        cookie.setAttribute("SameSite", "Lax")
        response.addCookie(cookie)
    }
}
