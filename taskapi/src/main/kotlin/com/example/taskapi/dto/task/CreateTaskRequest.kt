package com.example.taskapi.dto.task

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateTaskRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 100)
    val title: String,

    @field:Size(max = 500)
    val description: String? = null,

    val assigneeId: Long? = null
)