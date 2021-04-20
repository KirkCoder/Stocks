package ru.kcoder.stocks.domain.stock

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface StocksRepository {
    fun getAllStocks(): Single<List<Stock>>
    fun selectStock(id: String): Completable
    fun observeSelectedStock(): Observable<Stock>
}