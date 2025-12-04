package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable =  false)
    var priority: TaskPriority,

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    val board: Board,

    @ManyToOne
    @JoinColumn(name = "column_id")
    val column: BoardColumn,

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    var assignee: User? = null,

    @OneToMany(mappedBy = "task", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: List<TaskComment> = emptyList(),

    @Embedded
    var audit: AuditFields = AuditFields()
): BaseEntity()
