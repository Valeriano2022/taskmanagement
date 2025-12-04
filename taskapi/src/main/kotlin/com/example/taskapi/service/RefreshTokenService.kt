package com.example.taskapi.service

import com.example.taskapi.exception.UnauthorizedOperationException
import com.example.taskapi.model.RefreshToken
import com.example.taskapi.model.User
import com.example.taskapi.repository.RefreshTokenRepository
import com.example.taskapi.repository.UserRepository
import com.example.taskapi.security.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    @Value("\${jwt.refreshExpiration}")
    private val refreshExpirationMs: Long
) {

    fun createRefreshToken(user: User): RefreshToken {
        val expiry = Instant.now().plusMillis(refreshExpirationMs)

        val token = RefreshToken(
            user = user,
            token = jwtUtil.generateRefreshToken(user.email),
            expiryDate = expiry
        )

        return refreshTokenRepository.save(token)
    }

    fun validateToken(token: String): RefreshToken {
        val refreshToken = refreshTokenRepository.findByToken(token)
            ?: throw UnauthorizedOperationException("Invalid refresh token")

        if (refreshToken.revoked) {
            throw UnauthorizedOperationException("Refresh token has been revoked")
        }

        if (refreshToken.expiryDate.isBefore(Instant.now())) {
            refreshTokenRepository.save(refreshToken.copy(expired = true))
            throw UnauthorizedOperationException("Refresh token has expired")
        }

        return refreshToken
    }

    fun revokeToken(token: String) {
        val refreshToken = refreshTokenRepository.findByToken(token)
            ?: return

        val updated = refreshToken.copy(revoked = true)
        refreshTokenRepository.save(updated)
    }

    @Transactional
    fun revokeAllTokensForUser(userId: Long) {
        refreshTokenRepository.deleteByUserId(userId)
    }

    fun rotateToken(oldToken: String): RefreshToken {
        val existing = validateToken(oldToken)

        revokeToken(oldToken)

        return createRefreshToken(existing.user)
    }

    fun findByToken(token: String): RefreshToken? {
       return refreshTokenRepository.findByToken(token)
    }

    fun saveToken(token: RefreshToken): RefreshToken {
        return refreshTokenRepository.save(token)
    }
}
