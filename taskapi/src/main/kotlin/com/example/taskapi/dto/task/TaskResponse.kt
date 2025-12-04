package com.example.taskapi.dto.task

import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.dto.user.UserResponse
import com.example.taskapi.model.TaskPriority
import java.time.LocalDateTime

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val priority: TaskPriority?,
    val columnId: Long,
    val columnName: String,
    val assignee: UserResponse?,
    val comments: List<TaskCommentResponse>
)