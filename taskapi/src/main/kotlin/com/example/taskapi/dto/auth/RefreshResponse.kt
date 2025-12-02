package com.example.taskapi.dto.auth

import com.example.taskapi.dto.user.UserResponse

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)
