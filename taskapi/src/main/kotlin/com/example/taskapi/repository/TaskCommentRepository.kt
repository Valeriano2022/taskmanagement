package com.example.taskapi.repository

import com.example.taskapi.model.TaskComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskCommentRepository : JpaRepository<TaskComment, Long> {

    fun findAllByTaskId(taskId: Long): List<TaskComment>
}
