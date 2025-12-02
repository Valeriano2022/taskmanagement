package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true)
    var description: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    val board: Board,

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    var assignee: User? = null,

    @OneToMany(mappedBy = "task", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: List<TaskComment> = emptyList()
): BaseEntity()
