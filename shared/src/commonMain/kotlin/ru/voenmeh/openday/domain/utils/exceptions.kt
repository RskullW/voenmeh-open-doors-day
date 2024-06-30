package ru.voenmeh.openday.domain.utils

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException

class ConnectionException: Throwable(message = "Нет подключения к интернету")

fun isConnectionException(e: Throwable): Boolean {
    return when (e) {
        is ConnectTimeoutException,
        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        is ConnectionException
        -> true
        else -> false
    }
}