package com.example.taskapi.exception

class InvalidRequestException(message: String) :
    BaseException(message, 400)