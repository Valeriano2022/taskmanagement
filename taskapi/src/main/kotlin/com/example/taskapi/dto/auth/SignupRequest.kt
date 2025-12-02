package com.example.taskapi.dto.auth

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 30)
    val email: String,

    @field:NotBlank
    @field:Size(min = 6, max = 100)
    val password: String
)
