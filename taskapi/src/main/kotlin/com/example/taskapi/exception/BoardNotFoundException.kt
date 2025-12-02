package com.example.taskapi.exception

class BoardNotFoundException(id: Long) :
    BaseException("Board not found: $id", 404)