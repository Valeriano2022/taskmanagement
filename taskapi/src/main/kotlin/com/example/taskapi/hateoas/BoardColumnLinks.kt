package com.example.taskapi.hateoas

import com.example.taskapi.controller.BoardColumnController
import com.example.taskapi.controller.BoardController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class BoardColumnLinks {

    fun self(boardId: Long?, columnId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .slash(columnId)
            .withSelfRel()

    fun board(boardId: Long?) =
        linkTo(BoardController::class.java)
            .slash(boardId)
            .withRel("board")

    fun update(boardId: Long?, columnId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .slash(columnId)
            .withRel("update")

    fun delete(boardId: Long?, columnId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .slash(columnId)
            .withRel("delete")

    fun list(boardId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .withRel("columns")

    fun create(boardId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .withRel("create")

    fun reorder(boardId: Long?) =
        linkTo(BoardColumnController::class.java)
            .slash(boardId)
            .slash("columns")
            .slash("reorder")
            .withRel("reorder")

    fun <T : Any> addTo(model: EntityModel<T>, boardId: Long?, columnId: Long?): EntityModel<T> =
        model
            .add(self(boardId, columnId))
            .add(board(boardId))
            .add(update(boardId, columnId))
            .add(delete(boardId, columnId))
            .add(list(boardId))
            .add(create(boardId))
            .add(reorder(boardId))
}
