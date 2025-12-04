package com.example.taskapi.service

import com.example.taskapi.dto.column.BoardColumnResponse
import com.example.taskapi.dto.column.CreateBoardColumnRequest
import com.example.taskapi.dto.column.UpdateBoardColumnRequest
import com.example.taskapi.model.BoardColumn
import com.example.taskapi.repository.BoardColumnRepository
import com.example.taskapi.repository.BoardRepository
import com.example.taskapi.utils.mapper.BoardMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardColumnService(
    private val boardRepository: BoardRepository,
    private val columnRepository: BoardColumnRepository,
    private val boardMapper: BoardMapper
) {

    @Transactional(readOnly = true)
    fun getColumnsForBoard(boardId: Long, pageable: Pageable): Page<BoardColumnResponse> {
        val page = columnRepository.findByBoardIdOrderByPositionAsc(boardId, pageable)
        return page.map { boardMapper.toColumnResponse(it) }
    }


    @Transactional
    fun createColumn(boardId: Long, request: CreateBoardColumnRequest): BoardColumnResponse {
        val board = boardRepository.findById(boardId)
            .orElseThrow { IllegalArgumentException("Board $boardId not found") }

        val newColumn = BoardColumn(
            board = board,
            name = request.name,
            position = request.position
        )

        val saved = columnRepository.save(newColumn)
        return boardMapper.toColumnResponse(saved)
    }

    @Transactional
    fun updateColumn(boardId: Long, columnId: Long, request: UpdateBoardColumnRequest): BoardColumnResponse {
        val column = columnRepository.findById(columnId)
            .orElseThrow { IllegalArgumentException("Column $columnId not found") }

        if (column.board.id != boardId) {
            throw IllegalArgumentException("Column $columnId does not belong to board $boardId")
        }

        val updated = column.copy(
            name = request.name,
            position = request.position
        )

        val saved = columnRepository.save(updated)
        return boardMapper.toColumnResponse(saved)
    }

    @Transactional
    fun deleteColumn(boardId: Long, columnId: Long) {
        val column = columnRepository.findById(columnId)
            .orElseThrow { IllegalArgumentException("Column $columnId not found") }

        if (column.board.id != boardId) {
            throw IllegalArgumentException("Column $columnId does not belong to board $boardId")
        }

        columnRepository.delete(column)
    }

    @Transactional
    fun reorderColumns(boardId: Long, newOrder: List<Long>) {
        val columns = columnRepository.findByBoardIdOrderByPositionAsc(boardId)

        if (columns.size != newOrder.size) {
            throw IllegalArgumentException("Column count mismatch")
        }

        val positionMap = newOrder.withIndex().associate { (position, id) -> id to position }

        val updatedColumns = columns.map { col ->
            val pos = positionMap[col.id]
                ?: throw IllegalArgumentException("Column ${col.id} missing in reorder list")

            col.copy(position = pos)
        }

        columnRepository.saveAll(updatedColumns)
    }
}
