package com.example.taskapi.dto.member

import com.example.taskapi.dto.user.UserResponse
import com.example.taskapi.model.Role

data class BoardMemberResponse(
    val id: Long,
    val user: UserResponse,
    val role: Role
)

