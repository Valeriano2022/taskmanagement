package com.example.taskapi.dto.comment

import com.example.taskapi.dto.user.UserResponse

data class TaskCommentResponse(
    val id: Long,
    val content: String,
    val author: UserResponse
)
