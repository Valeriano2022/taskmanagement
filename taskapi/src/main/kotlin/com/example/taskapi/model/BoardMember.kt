package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "board_members")
data class BoardMember(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    val board: Board,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
): BaseEntity()
