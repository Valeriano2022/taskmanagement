package com.example.taskapi.hateoas

import com.example.taskapi.controller.TaskController
import com.example.taskapi.controller.CommentController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class TaskLinks {
    fun self(boardId: Long, taskId: Long) =
        linkTo(TaskController::class.java)
            .slash(boardId)
            .slash("tasks")
            .slash(taskId)
            .withSelfRel()

    fun comments(taskId: Long) =
        linkTo(CommentController::class.java)
            .slash(taskId)
            .slash("comments")
            .withRel("comments")

    fun update(boardId: Long, taskId: Long) =
        linkTo(TaskController::class.java)
            .slash(boardId)
            .slash("tasks")
            .slash(taskId)
            .withRel("update")

    fun delete(boardId: Long, taskId: Long) =
        linkTo(TaskController::class.java)
            .slash(boardId)
            .slash("tasks")
            .slash(taskId)
            .withRel("delete")

    fun <T: Any> addTo(model: EntityModel<T>, boardId: Long, taskId: Long): EntityModel<T> =
        model
            .add(self(boardId, taskId))
            .add(update(boardId, taskId))
            .add(delete(boardId, taskId))
            .add(comments(taskId))
}
