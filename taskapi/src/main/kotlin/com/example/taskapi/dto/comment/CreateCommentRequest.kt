package com.example.taskapi.dto.comment

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCommentRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 300)
    val content: String
)
