package com.example.taskapi.repository

import com.example.taskapi.model.BoardMember
import com.example.taskapi.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardMemberRepository : JpaRepository<BoardMember, Long> {

    fun findByBoardIdAndUserId(boardId: Long, userId: Long): BoardMember?

    fun existsByBoardIdAndUserId(boardId: Long, userId: Long): Boolean

    fun findAllByBoardId(boardId: Long): List<BoardMember>

    fun deleteByBoardIdAndUserId(boardId: Long, userId: Long)

    fun existsByBoardIdAndUserIdAndRole(boardId: Long, userId: Long, role: Role): Boolean

}
