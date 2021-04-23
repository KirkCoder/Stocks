package ru.kcoder.stocks.data.storage

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import ru.kcoder.stocks.data.dto.StockRateDto
import ru.kcoder.stocks.data.dto.StockRateStreamDto
import ru.kcoder.stocks.data.network.StocksApi
import javax.inject.Inject

class StockRateDataStore @Inject constructor(
    private val stocksApi: StocksApi,
    private val okHttpClient: OkHttpClient,
    private val stockRateStreamRequestMapper: StockRateStreamRequestMapper,
    private val stockRateStreamDtoMapper: StockRateStreamDto.Mapper,
    private val streamRequest: Request,
) {

    fun getStockRate(id: String): Single<StockRateDto> {
        return stocksApi.getStockRate(id)
    }

    fun getStockRateStream(
        selectedStockId: String,
        previousStockId: String?
    ): Observable<StockRateStreamDto> {
        val subject = PublishSubject.create<StockRateStreamDto>()
        okHttpClient.newWebSocket(streamRequest, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val request =
                    stockRateStreamRequestMapper.getRequest(selectedStockId, previousStockId)
                webSocket.send(request)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                subject.onNext(stockRateStreamDtoMapper.map(text))
            }
        })
        return subject
    }
}