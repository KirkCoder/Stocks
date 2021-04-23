package ru.kcoder.stocks.data.network

class ServerError(
    override val message: String?
) : Throwable(message)