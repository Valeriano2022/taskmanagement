package com.example.taskapi.controller

import com.example.taskapi.dto.board.CreateBoardRequest
import com.example.taskapi.dto.board.UpdateBoardRequest
import com.example.taskapi.dto.board.BoardResponse
import com.example.taskapi.exception.BoardNotFoundException
import com.example.taskapi.hateoas.BoardLinks
import com.example.taskapi.utils.mapper.BoardMapper
import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.service.BoardService
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
@RequestMapping("/api/boards")
class BoardController(
    private val boardService: BoardService,
    private val boardLinks: BoardLinks,
    private val pagedAssembler: PagedResourcesAssembler<BoardResponse>
) {

    @PostMapping
    fun createBoard(authentication: Authentication, @Valid @RequestBody request: CreateBoardRequest)
            : ResponseEntity<EntityModel<BoardResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val board = boardService.createBoard(principal.userId, request)
        return ResponseEntity.ok(boardLinks.addTo(EntityModel.of(board), board.id))
    }

    @GetMapping("/{boardId}")
    fun getBoard(authentication: Authentication, @PathVariable boardId: Long)
            : ResponseEntity<EntityModel<BoardResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val board = boardService.getBoard(boardId, principal.userId)
        return ResponseEntity.ok(boardLinks.addTo(EntityModel.of(board), boardId))
    }

    @PutMapping("/{boardId}")
    fun updateBoard(authentication: Authentication, @PathVariable boardId: Long, @Valid @RequestBody request: UpdateBoardRequest)
            : ResponseEntity<EntityModel<BoardResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val board = boardService.updateBoard(boardId, principal.userId, request)
        return ResponseEntity.ok(boardLinks.addTo(EntityModel.of(board), boardId))
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(authentication: Authentication, @PathVariable boardId: Long)
            : ResponseEntity<EntityModel<Map<String, String>>> {
        val principal = authentication.principal as CustomUserPrincipal
        boardService.deleteBoard(boardId, principal.userId)
        return ResponseEntity.ok(EntityModel.of(mapOf("message" to "Board deleted")))
    }

    @GetMapping
    fun listBoards(authentication: Authentication, pageable: Pageable)
            : ResponseEntity<PagedModel<EntityModel<BoardResponse>>> {
        val principal = authentication.principal as CustomUserPrincipal
        val page = boardService.listBoards(principal.userId, pageable).map { it }
        val model = pagedAssembler.toModel(page)
        { dto -> boardLinks.addTo(EntityModel.of(dto), dto.id) }
            .cast<BoardResponse>()
        return ResponseEntity.ok(model)
    }
}
