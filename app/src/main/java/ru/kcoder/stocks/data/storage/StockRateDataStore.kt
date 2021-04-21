package ru.kcoder.stocks.data.storage

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.*
import ru.kcoder.stocks.config.ApiUrlProvider
import ru.kcoder.stocks.data.dto.StockRateDto
import ru.kcoder.stocks.data.dto.StockRateStreamDto
import ru.kcoder.stocks.data.dto.StockRateStreamRequestDto
import ru.kcoder.stocks.data.network.StocksApi
import timber.log.Timber
import javax.inject.Inject

class StockRateDataStore @Inject constructor(
    private val stocksApi: StocksApi,
    private val okHttpClient: OkHttpClient,
    private val stockRateStreamRequestDtoMapper: StockRateStreamRequestDto.Mapper,
    private val streamRequest: Request,
    private val gson: Gson,
) {

    fun getStockRate(id: String): Single<StockRateDto> {
        getStockRateStream(id)
        return stocksApi.getStockRate(id)
    }

    fun getStockRateStream(id: String) {
        okHttpClient.newWebSocket(streamRequest, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val request = gson.toJson(stockRateStreamRequestDtoMapper.map(id))
                webSocket.send(request)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Timber.d("@@@@@ $text")
            }
        })
    }
}