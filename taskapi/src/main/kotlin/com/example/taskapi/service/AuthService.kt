package com.example.taskapi.service

import com.example.taskapi.dto.auth.*
import com.example.taskapi.dto.user.UserResponse
import com.example.taskapi.exception.EmailAlreadyExistsException
import com.example.taskapi.exception.InvalidRequestException
import com.example.taskapi.exception.UnauthorizedOperationException
import com.example.taskapi.exception.UserNotFoundException
import com.example.taskapi.model.User
import com.example.taskapi.repository.UserRepository
import com.example.taskapi.security.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,
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

    fun login(request: LoginRequest): LoginResponse {
        val email = request.email.trim().lowercase()

        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException(email)

        if (!passwordEncoder.matches(request.password, user.password))
            throw UnauthorizedOperationException("Invalid email or password")

        val refreshToken = refreshTokenService.createRefreshToken(user.id!!)

        return LoginResponse(
            accessToken = jwtUtil.generateAccessToken(user.id!!, user.email),
            refreshToken = refreshToken.token,
            user = UserResponse(user.id!!, user.email)
        )
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
    fun logout(userId: Long) {
        refreshTokenService.revokeAllTokensForUser(userId)
    }
}
