package com.example.taskapi.dto.board

import com.example.taskapi.dto.column.BoardColumnResponse
import com.example.taskapi.dto.member.BoardMemberResponse
import com.example.taskapi.dto.user.UserResponse

data class BoardResponse(
    val id: Long?,
    val name: String?,
    val owner: UserResponse?,
    val members: List<BoardMemberResponse>?,
    val columns: List<BoardColumnResponse>?
)