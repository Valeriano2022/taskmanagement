package com.example.taskapi.service

import com.example.taskapi.dto.member.BoardMemberResponse
import com.example.taskapi.dto.member.InviteMemberRequest
import com.example.taskapi.exception.MemberAlreadyInvitedException
import com.example.taskapi.exception.UserNotFoundException
import com.example.taskapi.model.BoardMember
import com.example.taskapi.model.Role
import com.example.taskapi.repository.BoardMemberRepository
import com.example.taskapi.repository.BoardRepository
import com.example.taskapi.repository.UserRepository
import com.example.taskapi.utils.mapper.BoardMemberMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val boardMemberRepository: BoardMemberRepository,
    private val boardMemberMapper: BoardMemberMapper,
    private val securityService: SecurityService
) {

    @Transactional
    fun inviteMember(boardId: Long, ownerId: Long, request: InviteMemberRequest?){
        securityService.assertIsOwner(boardId, ownerId)

        val email = request?.email?.trim()?.lowercase().toString()
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException(email)

        if (boardMemberRepository.existsByBoardIdAndUserId(boardId, user.id!!)) {
            throw MemberAlreadyInvitedException(email)
        }

        val board = boardRepository.findById(boardId).orElseThrow()

        val membership = BoardMember(
            board = board,
            user = user,
            role = Role.MEMBER
        )

        boardMemberRepository.save(membership)
    }

    fun listMembers(boardId: Long, requesterId: Long): List<BoardMemberResponse> {
        securityService.assertIsMember(boardId, requesterId)
        val found = boardMemberRepository.findAllByBoardId(boardId)
        return found.map{boardMemberMapper.toResponse(it)}
    }

    @Transactional
    fun removeMember(boardId: Long, ownerId: Long, userIdToRemove: Long) {
        securityService.assertIsOwner(boardId, ownerId)

        val exists = boardMemberRepository.existsByBoardIdAndUserId(boardId, userIdToRemove)
        if (!exists) throw UserNotFoundException("User $userIdToRemove is not a member of this board")

        boardMemberRepository.deleteByBoardIdAndUserId(boardId, userIdToRemove)
    }
}
