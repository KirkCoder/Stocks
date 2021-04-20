package ru.kcoder.stocks.data.storage

import io.reactivex.Single
import ru.kcoder.stocks.data.dto.StockRateDto
import ru.kcoder.stocks.data.network.StocksApi
import javax.inject.Inject

class StockRateDataStore @Inject constructor(
    private val stocksApi: StocksApi,
) {

    fun getStockRate(id: String): Single<StockRateDto> {
        return stocksApi.getStockRate(id)
    }
}