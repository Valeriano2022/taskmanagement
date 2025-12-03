package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(name = "task_comments")
data class TaskComment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val content: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    val task: Task,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val author: User,

    @Embedded
    var audit: AuditFields = AuditFields()
): BaseEntity()
