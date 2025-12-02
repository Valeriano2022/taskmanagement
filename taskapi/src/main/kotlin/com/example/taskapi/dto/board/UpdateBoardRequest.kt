package com.example.taskapi.dto.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateBoardRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val name: String
)