package com.churickoid.filmity.data

open class AppException : RuntimeException()

class AuthException : AppException()

class EmailExistException : AppException()

class IncorrectCodeException : AppException()

const val DEFAULT_STATE = 0
const val LOADING_STATE = 1

const val INTERNET_ERROR = "Need internet connection"
const val TOKEN_ERROR = "Смените токен на другой или создайте новый"


