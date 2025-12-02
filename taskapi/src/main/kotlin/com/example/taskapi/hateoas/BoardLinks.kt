package com.example.taskapi.hateoas

import com.example.taskapi.controller.BoardController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class BoardLinks {

    fun self(boardId: Long) =
        linkTo(BoardController::class.java)
            .slash(boardId)
            .withSelfRel()

    fun tasks(boardId: Long) =
        linkTo(BoardController::class.java)
            .slash(boardId)
            .slash("tasks")
            .withRel("tasks")

    fun update(boardId: Long) =
        linkTo(BoardController::class.java)
            .slash(boardId)
            .withRel("update")

    fun delete(boardId: Long) =
        linkTo(BoardController::class.java)
            .slash(boardId)
            .withRel("delete")

    fun list() =
        linkTo(BoardController::class.java)
            .withRel("boards")

    fun <T : Any> addTo(model: EntityModel<T>, boardId: Long): EntityModel<T> =
        model
            .add(self(boardId))
            .add(tasks(boardId))
            .add(update(boardId))
            .add(delete(boardId))
            .add(list())
}
