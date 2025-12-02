package com.example.taskapi.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val messaging: SimpMessagingTemplate
) {

    fun <T: Any> broadcastBoardEvent(boardId: Long, event: T) {
        messaging.convertAndSend("/topic/boards/$boardId/events", event)
    }

    fun <T: Any> broadcastTaskEvent(boardId: Long, taskId: Long, event: T) {
        messaging.convertAndSend("/topic/boards/$boardId/tasks/$taskId/events", event)
    }

    fun <T: Any> broadcastCommentEvent(taskId: Long, event: T) {
        messaging.convertAndSend("/topic/tasks/$taskId/comments/events", event)
    }
}
