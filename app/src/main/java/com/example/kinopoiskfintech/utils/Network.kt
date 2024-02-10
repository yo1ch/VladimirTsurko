package com.example.kinopoiskfintech.utils

import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T>{
    if (code() in 200..299){
        return Result.success(body()!!)
    }
    if (code() in 400..499){
        return Result.failure<T>(HttpClientErrorException.create(code()))
    }
    if (code() in 500..599){
        return Result.failure<T>(HttpServerErrorException.create(code()))
    }
    return Result.failure(Throwable(code().toString() + message()))
}