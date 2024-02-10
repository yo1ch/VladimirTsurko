package com.example.kinopoiskfintech.utils


sealed class HttpClientErrorException(
    val code: Int,
    message: String,
) : Exception(message) {

    class BadRequest: HttpClientErrorException(400, "Bad Request")
    class Unauthorized : HttpClientErrorException(401, "Unauthorized")
    class NotFound : HttpClientErrorException(404, "Not Found")

    companion object {
        fun create(code: Int): HttpClientErrorException {
            return when (code) {
                400 -> BadRequest()
                401 -> Unauthorized()
                404 -> NotFound()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

sealed class HttpServerErrorException(
    val code: Int,
    message: String,
) : Exception(message) {
    class InternalServerError : HttpServerErrorException(500, "Internal Server Error")
    class NotImplemented : HttpServerErrorException(501, "Not Implemented")
    class BadGateway : HttpServerErrorException(502, "Bad Gateway")
    companion object {
        fun create(code: Int): HttpServerErrorException {
            return when (code) {
                500 -> InternalServerError()
                501 -> NotImplemented()
                502 -> BadGateway()
                else -> throw IllegalArgumentException()
            }
        }
    }

}