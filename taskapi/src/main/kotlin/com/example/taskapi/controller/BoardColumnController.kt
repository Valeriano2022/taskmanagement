package com.example.taskapi.controller

import com.example.taskapi.dto.column.BoardColumnResponse
import com.example.taskapi.dto.column.CreateBoardColumnRequest
import com.example.taskapi.dto.column.UpdateBoardColumnRequest
import com.example.taskapi.hateoas.BoardColumnLinks
import com.example.taskapi.service.BoardColumnService
import com.example.taskapi.utils.cast
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/boards/{boardId}/columns")
class BoardColumnController(
    private val columnService: BoardColumnService,
    private val columnLinks: BoardColumnLinks,
    private val pagedAssembler: PagedResourcesAssembler<BoardColumnResponse>
) {

    @PostMapping
    fun createColumn(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @Valid @RequestBody request: CreateBoardColumnRequest
    ): ResponseEntity<EntityModel<BoardColumnResponse>> {
        val column = columnService.createColumn(boardId, request)
        return ResponseEntity.ok(columnLinks.addTo(EntityModel.of(column), boardId, column.id))
    }

    @PutMapping("/{columnId}")
    fun updateColumn(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @PathVariable columnId: Long,
        @Valid @RequestBody request: UpdateBoardColumnRequest
    ): ResponseEntity<EntityModel<BoardColumnResponse>> {
        val updated = columnService.updateColumn(boardId, columnId, request)
        return ResponseEntity.ok(columnLinks.addTo(EntityModel.of(updated), boardId, columnId))
    }

    @DeleteMapping("/{columnId}")
    fun deleteColumn(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @PathVariable columnId: Long
    ): ResponseEntity<Void> {
        columnService.deleteColumn(boardId, columnId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun listColumns(
        authentication: Authentication,
        @PathVariable boardId: Long,
        pageable: Pageable
    ): ResponseEntity<PagedModel<EntityModel<BoardColumnResponse>>> {
        val list = columnService.getColumnsForBoard(boardId, pageable)
        val model = pagedAssembler.toModel(list, columnLinks.withBoardId(boardId)).cast<BoardColumnResponse>()
        return ResponseEntity.ok(model)
    }

    @PutMapping("/reorder")
    fun reorderColumns(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @RequestBody newOrder: List<Long>
    ): ResponseEntity<Void> {
        columnService.reorderColumns(boardId, newOrder)
        return ResponseEntity.noContent().build()
    }

    private fun BoardColumnLinks.withBoardId(boardId: Long): (BoardColumnResponse) -> EntityModel<BoardColumnResponse> =
        { col -> addTo(EntityModel.of(col), boardId, col.id) }
}
