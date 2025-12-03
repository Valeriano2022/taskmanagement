package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @OneToMany(mappedBy = "user")
    val memberships: List<BoardMember> = emptyList(),

    @Embedded
    var audit: AuditFields = AuditFields()
): BaseEntity()
