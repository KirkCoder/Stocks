package ru.kcoder.stocks.domain.rate

import io.reactivex.Observable
import io.reactivex.Single

interface StockRateRepository {
    fun getStockRate(selectedStockId: String): Single<StockRate>

    fun getStockRateStream(selectedStockId: String, previousStockId: String?): Observable<StockRateLive>
}