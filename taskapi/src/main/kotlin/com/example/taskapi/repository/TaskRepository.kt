package com.example.taskapi.repository

import com.example.taskapi.model.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findAllByBoardId(boardId: Long, pageable: Pageable): Page<Task>

    fun findAllByAssigneeId(userId: Long): List<Task>

}
