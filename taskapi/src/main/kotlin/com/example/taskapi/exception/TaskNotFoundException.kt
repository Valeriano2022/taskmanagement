package com.example.taskapi.exception

class TaskNotFoundException(id: Long) :
    BaseException("Task not found: $id", 404)