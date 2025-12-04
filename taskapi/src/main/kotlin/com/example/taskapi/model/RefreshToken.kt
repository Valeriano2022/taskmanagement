package com.example.taskapi.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false)
    val expiryDate: Instant,

    @Column(nullable = false)
    var expired: Boolean = false,

    @Column(nullable = false)
    var revoked: Boolean = false,

    @Embedded
    var audit: AuditFields = AuditFields()
): BaseEntity()
