package com.example.taskapi.dto.websocket

import com.example.taskapi.dto.board.BoardResponse

data class BoardEvent(
    val type: String,
    val board: BoardResponse
)
