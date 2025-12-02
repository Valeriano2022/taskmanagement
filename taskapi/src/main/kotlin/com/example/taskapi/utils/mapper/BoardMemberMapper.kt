package com.example.taskapi.utils.mapper

import com.example.taskapi.dto.member.BoardMemberResponse
import com.example.taskapi.model.BoardMember
import org.springframework.stereotype.Component

@Component
class BoardMemberMapper(
    private val userMapper: UserMapper
) {

    fun toResponse(member: BoardMember): BoardMemberResponse =
        BoardMemberResponse(
            id = member.id!!,
            user = userMapper.toResponse(member.user),
            role = member.role
        )
}
