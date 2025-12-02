package com.example.taskapi.exception

class ForbiddenException(message: String) :
    BaseException(message, 403)