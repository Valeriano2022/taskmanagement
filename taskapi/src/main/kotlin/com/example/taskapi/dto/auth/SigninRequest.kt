package com.example.taskapi.dto.auth

import jakarta.validation.constraints.NotBlank

data class SigninRequest(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String
)
