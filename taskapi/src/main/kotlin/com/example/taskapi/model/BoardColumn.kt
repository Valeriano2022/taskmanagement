package com.example.taskapi.model

import jakarta.persistence.*

@Entity
@Table(
    name = "board_columns",
    uniqueConstraints = [
        UniqueConstraint(name = "unique_board_column", columnNames = ["board_id", "name"])
    ]
)
data class BoardColumn(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    val board: Board,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val position: Int,

    @Embedded
    var audit: AuditFields = AuditFields()
): BaseEntity()
