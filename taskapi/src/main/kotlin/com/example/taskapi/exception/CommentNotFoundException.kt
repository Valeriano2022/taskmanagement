package com.example.taskapi.exception

class CommentNotFoundException(id: Long) :
    BaseException("Comment not found: $id", 404)