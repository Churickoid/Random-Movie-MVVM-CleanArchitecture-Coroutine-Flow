package com.example.randommovie.data

open class AppException : RuntimeException()

class AuthException : AppException()

class EmailExistException : AppException()

class IncorrectCodeException : AppException()

const val DEFAULT_STATE = 0
const val LOADING_STATE = 1

const val INTERNET_ERROR = "Need internet connection"


