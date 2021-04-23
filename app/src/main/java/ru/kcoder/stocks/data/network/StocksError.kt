package ru.kcoder.stocks.data.network

sealed class StocksError(
    message: String? = null
) : Throwable(message) {
    class Server(message: String?) : StocksError(message)
    class StreamConnection(val outError: Throwable?) : StocksError()
}