package com.example.taskapi.service

import com.example.taskapi.exception.BoardNotFoundException
import com.example.taskapi.exception.ForbiddenException
import com.example.taskapi.repository.BoardMemberRepository
import com.example.taskapi.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val boardRepository: BoardRepository,
    private val boardMemberRepository: BoardMemberRepository
) {

    fun assertBoardExists(boardId: Long) {
        if (!boardRepository.existsById(boardId)) {
            throw BoardNotFoundException(boardId)
        }
    }

    fun assertIsOwner(boardId: Long, userId: Long) {
        val board = boardRepository.findById(boardId)
            .orElseThrow { BoardNotFoundException(boardId) }

        if (board.owner.id != userId) {
            throw ForbiddenException("Only board owner can perform this action.")
        }
    }

    fun assertIsMember(boardId: Long, userId: Long) {
        val isMember = boardMemberRepository.existsByBoardIdAndUserId(boardId, userId)
        if (!isMember) {
            throw ForbiddenException("You are not a member of this board.")
        }
    }

    fun assertIsOwnerOrMember(boardId: Long, userId: Long) {
        val isMember = boardMemberRepository.existsByBoardIdAndUserId(boardId, userId)
        if (!isMember) {
            throw ForbiddenException("You must be a board member.")
        }
    }
}
