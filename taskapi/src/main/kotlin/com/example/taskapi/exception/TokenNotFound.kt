package com.example.taskapi.exception

class TokenNotFound():
    BaseException("Token not found", 404)