package com.example.taskapi.service

import com.example.taskapi.dto.comment.CreateCommentRequest
import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.exception.TaskNotFoundException
import com.example.taskapi.model.TaskComment
import com.example.taskapi.repository.*
import com.example.taskapi.utils.mapper.CommentMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val taskRepository: TaskRepository,
    private val commentRepository: TaskCommentRepository,
    private val userRepository: UserRepository,
    private val boardMemberRepository: BoardMemberRepository,
    private val commentMapper: CommentMapper
) {

    @Transactional
    fun createComment(taskId: Long, userId: Long, request: CreateCommentRequest): TaskCommentResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }

        val isMember = boardMemberRepository.existsByBoardIdAndUserId(task.board.id!!, userId)

        if (!isMember) {
            throw com.example.taskapi.exception.ForbiddenException(
                "Only board members may comment"
            )
        }

        val author = userRepository.findById(userId).orElseThrow()

        val saved =  commentRepository.save(
            TaskComment(
                content = request.content.trim(),
                task = task,
                author = author
            )
        )
        return commentMapper.toResponse(saved)
    }

    fun listComments(taskId: Long) =
        taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }
            .comments.map{ commentMapper.toResponse(it) }
}
