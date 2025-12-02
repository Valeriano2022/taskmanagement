package com.example.taskapi.service

import com.example.taskapi.dto.comment.CreateCommentRequest
import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.dto.websocket.CommentEvent
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
    private val commentMapper: CommentMapper,
    private val notifications: NotificationService
) {

    @Transactional
    fun createComment(taskId: Long, userId: Long, request: CreateCommentRequest): TaskCommentResponse {
        val task = taskRepository.findById(taskId).orElseThrow { TaskNotFoundException(taskId) }

        if (!boardMemberRepository.existsByBoardIdAndUserId(task.board.id!!, userId)) {
            throw com.example.taskapi.exception.ForbiddenException("Only board members may comment")
        }

        val author = userRepository.findById(userId).orElseThrow()

        val saved = commentRepository.save(
            TaskComment(
                content = request.content.trim(),
                task = task,
                author = author
            )
        )

        val dto = commentMapper.toResponse(saved)
        notifications.commentEvent(taskId, CommentEvent("COMMENT_CREATED", dto))

        return dto
    }

    fun listComments(taskId: Long) =
        taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }
            .comments
            .map { commentMapper.toResponse(it) }
}
