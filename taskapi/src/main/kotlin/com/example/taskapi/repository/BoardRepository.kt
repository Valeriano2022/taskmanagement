package com.example.taskapi.repository

import com.example.taskapi.model.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByOwnerId(ownerId: Long, pageable: Pageable): Page<Board>
}
