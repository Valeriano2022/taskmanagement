package com.example.taskapi.dto.websocket

import com.example.taskapi.dto.task.TaskResponse

data class TaskEvent(
    val type: String,
    val task: TaskResponse
)