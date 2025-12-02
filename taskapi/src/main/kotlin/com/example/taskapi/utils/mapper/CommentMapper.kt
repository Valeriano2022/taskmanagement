package com.example.taskapi.utils.mapper

import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.model.TaskComment
import org.springframework.stereotype.Component

@Component
class CommentMapper(
    private val userMapper: UserMapper
) {

    fun toResponse(comment: TaskComment): TaskCommentResponse =
        TaskCommentResponse(
            id = comment.id!!,
            content = comment.content,
            author = userMapper.toResponse(comment.author)
        )
}
