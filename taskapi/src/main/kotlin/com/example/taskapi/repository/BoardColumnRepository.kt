package com.example.taskapi.repository

import com.example.taskapi.model.BoardColumn
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardColumnRepository : JpaRepository<BoardColumn, Long> {
    fun findByBoardIdOrderByPositionAsc(boardId: Long, pageable: Pageable): Page<BoardColumn>
    fun findByBoardIdOrderByPositionAsc(boardId: Long): List<BoardColumn>
}
