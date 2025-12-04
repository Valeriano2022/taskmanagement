package com.example.taskapi.repository

import com.example.taskapi.model.RefreshToken
import com.example.taskapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun findByToken(token: String): RefreshToken?

    fun deleteByUserId(userId: Long)

    fun findAllByUserAndExpiredIsFalseAndRevokedIsFalse(user: User): List<RefreshToken>
}
