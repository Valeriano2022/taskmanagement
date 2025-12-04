package com.example.taskapi.dto.task

import com.example.taskapi.model.TaskPriority
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateTaskRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 100)
    val title: String,

    @field:Size(max = 500)
    val description: String? = null,

    val priority: TaskPriority,

    val assigneeId: Long? = null
)