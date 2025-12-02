package com.example.taskapi.utils.mapper

import com.example.taskapi.model.User
import com.example.taskapi.dto.user.UserResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toResponse(user: User): UserResponse =
        UserResponse(
            id = user.id!!,
            email = user.email
        )
}
