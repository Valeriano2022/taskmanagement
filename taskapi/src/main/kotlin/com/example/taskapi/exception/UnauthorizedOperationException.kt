package com.example.taskapi.exception

class UnauthorizedOperationException(message: String) :
    BaseException(message, 401)