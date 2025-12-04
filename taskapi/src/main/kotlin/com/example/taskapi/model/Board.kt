package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "boards")
data class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    val owner: User,

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    val memberships: List<BoardMember> = emptyList(),

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tasks: List<Task> = emptyList(),

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("position ASC")
    val columns: List<BoardColumn> = emptyList(),

    @Embedded
    var audit: AuditFields = AuditFields()
) : BaseEntity()
