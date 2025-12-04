package com.example.taskapi.exception

class ColumnNotFoundException() :
    BaseException("Column not found", 404)