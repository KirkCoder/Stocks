package ru.kcoder.stocks.domain.rate

import io.reactivex.Single

interface StockRateRepository {
    fun getStockRate(id: String): Single<StockRate>
}