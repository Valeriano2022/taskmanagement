package com.example.taskapi.hateoas

import com.example.taskapi.controller.TaskController
import com.example.taskapi.controller.CommentController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class TaskLinks {

    fun self(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(TaskController::class.java)
                .getTask(null, boardId, taskId)
        ).withSelfRel()

    fun comments(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(CommentController::class.java)
                .listComments(boardId, taskId)
        ).withRel("comments")

    fun update(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(TaskController::class.java)
                .updateTask(null, boardId, taskId, null)
        ).withRel("update")

    fun delete(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(TaskController::class.java)
                .deleteTask(null, boardId, taskId)
        ).withRel("delete")

    fun <T : Any> addTo(model: EntityModel<T>, boardId: Long, taskId: Long): EntityModel<T> =
        model
            .add(self(boardId, taskId))
            .add(update(boardId, taskId))
            .add(delete(boardId, taskId))
            .add(comments(boardId, taskId))
}
