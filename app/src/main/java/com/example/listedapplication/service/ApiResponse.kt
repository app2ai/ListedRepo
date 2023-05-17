package com.example.listedapplication.service

import android.util.Log

/*
Collect API response dynamically for all type of request
T is generic class for response
*/
sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()

    object Error: ApiResponse<Nothing>()
}

/*
This inline function responsible for catching response and return into suitable
Response Block [SUCCESS or ERROR]
 */
inline fun <T> apiRequest(block: () -> T): ApiResponse<out T> = runCatching {
    ApiResponse.Success(block())
}.getOrElse {
    Log.e("ERROR", it.message ?: it.stackTraceToString())
    ApiResponse.Error
}
