package com.example.taskapi.service

import com.example.taskapi.dto.board.BoardResponse
import com.example.taskapi.dto.board.CreateBoardRequest
import com.example.taskapi.dto.board.UpdateBoardRequest
import com.example.taskapi.dto.websocket.BoardEvent
import com.example.taskapi.model.Board
import com.example.taskapi.model.BoardMember
import com.example.taskapi.model.Role
import com.example.taskapi.repository.BoardMemberRepository
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
    private val memberRepo: BoardMemberRepository,
    private val securityService: SecurityService,
    private val boardMapper: BoardMapper,
    private val notifications: NotificationService
) {

    @Transactional
    fun createBoard(ownerId: Long, request: CreateBoardRequest): BoardResponse {
        val owner = userRepository.findById(ownerId).orElseThrow()
        val board = boardRepository.save(
            Board(
                name = request.name,
                owner = owner
            )
        )
        memberRepo.save(BoardMember(
            board = board,
            user = owner,
            role = Role.OWNER
        ))
        val dto = boardMapper.toResponse(board)

        notifications.boardEvent(board.id!!, BoardEvent("BOARD_CREATED", dto))

        return dto
    }

    @Transactional
    fun updateBoard(boardId: Long, userId: Long, request: UpdateBoardRequest): BoardResponse {
        securityService.assertIsOwner(boardId, userId)

        val board = boardRepository.findById(boardId).orElseThrow()
        board.name = request.name.trim()

        val saved = boardRepository.save(board)
        val dto = boardMapper.toResponse(saved)

        notifications.boardEvent(boardId, BoardEvent("BOARD_UPDATED", dto))

        return dto
    }

    @Transactional
    fun deleteBoard(boardId: Long, userId: Long) {
        securityService.assertIsOwner(boardId, userId)
        boardRepository.deleteById(boardId)
        notifications.boardEvent(boardId, BoardEvent("BOARD_DELETED", BoardResponse(
            id = boardId,
            name = null,
            owner = null,
            members = null,
            columns = null
        )))
    }

    fun getBoard(boardId: Long, userId: Long): BoardResponse {
        securityService.assertIsOwnerOrMember(boardId, userId)
        val found = boardRepository.findById(boardId).orElseThrow()
        return boardMapper.toResponse(found)
    }

    @Transactional(readOnly = true)
    fun listBoards(ownerId: Long, pageable: Pageable): Page<BoardResponse> =
        boardRepository.findAllByOwnerId(ownerId, pageable).map { boardMapper.toResponse(it) }
}
