package com.example.taskapi.service

import com.example.taskapi.dto.board.BoardResponse
import com.example.taskapi.dto.board.CreateBoardRequest
import com.example.taskapi.dto.board.UpdateBoardRequest
import com.example.taskapi.exception.BoardNotFoundException
import com.example.taskapi.model.Board
import com.example.taskapi.repository.BoardRepository
import com.example.taskapi.repository.UserRepository
import com.example.taskapi.utils.mapper.BoardMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
    private val boardMapper: BoardMapper
) {

    fun createBoard(ownerId: Long, request: CreateBoardRequest): BoardResponse {
        val owner = userRepository.findById(ownerId)
            .orElseThrow { IllegalStateException("Owner not found") }

        val board = Board(
            name = request.name.trim(),
            owner = owner
        )

        val saved = boardRepository.save(board)
        return boardMapper.toResponse(saved)
    }

    fun updateBoard(boardId: Long, userId: Long, request: UpdateBoardRequest): BoardResponse {
        securityService.assertIsOwner(boardId, userId)

        val board = boardRepository.findById(boardId)
            .orElseThrow { BoardNotFoundException(boardId) }

        board.name = request.name.trim()

        val saved = boardRepository.save(board)
        return boardMapper.toResponse(saved)
    }

    fun deleteBoard(boardId: Long, userId: Long) {
        securityService.assertIsOwner(boardId, userId)
        boardRepository.deleteById(boardId)
    }

    fun getBoard(boardId: Long, userId: Long): BoardResponse {
        securityService.assertIsOwnerOrMember(boardId, userId)
        val found = boardRepository.findById(boardId)
            .orElseThrow { BoardNotFoundException(boardId) }
        return boardMapper.toResponse(found)
    }

    fun listBoards(ownerId: Long, pageable: Pageable): Page<BoardResponse> =
        boardRepository.findAllByOwnerId(ownerId, pageable).map{boardMapper.toResponse(it)}
}
