package com.example.taskapi.utils.mapper

import com.example.taskapi.dto.task.TaskResponse
import com.example.taskapi.model.Task
import org.springframework.stereotype.Component

@Component
class TaskMapper(
    private val userMapper: UserMapper,
    private val commentMapper: CommentMapper
) {

    fun toResponse(task: Task): TaskResponse =
        TaskResponse(
            id = task.id!!,
            title = task.title,
            description = task.description,
            assignee = task.assignee?.let { userMapper.toResponse(it) },
            comments = task.comments.map { commentMapper.toResponse(it) },
            priority = task.priority,
            columnId = task.column.id,
            columnName = task.column.name
        )
}
