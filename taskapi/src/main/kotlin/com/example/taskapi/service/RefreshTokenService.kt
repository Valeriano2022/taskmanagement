package com.example.taskapi.service

import com.example.taskapi.exception.UnauthorizedOperationException
import com.example.taskapi.model.RefreshToken
import com.example.taskapi.repository.RefreshTokenRepository
import com.example.taskapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,

    @Value("\${jwt.refreshExpiration}")
    private val refreshExpirationMs: Long
) {

    fun createRefreshToken(userId: Long): RefreshToken {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalStateException("User not found: $userId") }

        val expiry = Instant.now().plusMillis(refreshExpirationMs)

        val token = RefreshToken(
            user = user,
            token = UUID.randomUUID().toString(),
            expiryDate = expiry,
            revoked = false
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

    fun revokeAllTokensForUser(userId: Long) {
        refreshTokenRepository.deleteByUserId(userId)
    }

    fun rotateToken(oldToken: String): RefreshToken {
        val existing = validateToken(oldToken)

        revokeToken(oldToken)

        return createRefreshToken(existing.user.id!!)
    }
}
