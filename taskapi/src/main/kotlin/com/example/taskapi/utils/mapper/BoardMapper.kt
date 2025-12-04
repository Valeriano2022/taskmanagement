package com.example.taskapi.utils.mapper

import com.example.taskapi.dto.board.BoardResponse
import com.example.taskapi.dto.column.BoardColumnResponse
import com.example.taskapi.model.Board
import com.example.taskapi.model.BoardColumn
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
            members = board.memberships.map {
                memberMapper.toResponse(it)
            },
            columns = board.columns.sortedBy { it.position }.map { toColumnResponse(it) }
        )
    fun toColumnResponse(column: BoardColumn): BoardColumnResponse {
        return BoardColumnResponse(
            id = column.id,
            name = column.name,
            position = column.position
        )
    }
}
