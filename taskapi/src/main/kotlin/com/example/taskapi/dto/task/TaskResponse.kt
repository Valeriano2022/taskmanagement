package com.example.taskapi.dto.task

import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.dto.user.UserResponse
import java.time.LocalDateTime

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val assignee: UserResponse?,
    val comments: List<TaskCommentResponse>
)