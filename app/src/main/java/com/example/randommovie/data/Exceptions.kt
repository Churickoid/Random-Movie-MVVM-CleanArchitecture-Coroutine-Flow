package com.example.randommovie.data

open class AppException : RuntimeException()

class AuthException : AppException()

class EmailExistException : AppException()

class IncorrectCodeException : AppException()


