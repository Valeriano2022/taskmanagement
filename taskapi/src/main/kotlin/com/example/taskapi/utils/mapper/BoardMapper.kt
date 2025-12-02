package com.example.taskapi.utils.mapper

import com.example.taskapi.dto.board.BoardResponse
import com.example.taskapi.model.Board
import org.springframework.stereotype.Component

@Component
class BoardMapper(
    private val userMapper: UserMapper,
    private val memberMapper: BoardMemberMapper
) {

    fun toResponse(board: Board): BoardResponse =
        BoardResponse(
            id = board.id!!,
            name = board.name,
            owner = userMapper.toResponse(board.owner),
            members = board.memberships.map { memberMapper.toResponse(it) }
        )
}
