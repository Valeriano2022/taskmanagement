package com.example.taskapi.dto.websocket

import com.example.taskapi.dto.comment.TaskCommentResponse

data class CommentEvent(
    val type: String,
    val comment: TaskCommentResponse
)