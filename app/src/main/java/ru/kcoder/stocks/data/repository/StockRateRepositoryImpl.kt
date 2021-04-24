package ru.kcoder.stocks.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.kcoder.stocks.data.dto.StockRateStreamType
import ru.kcoder.stocks.data.storage.StockRateDataStore
import ru.kcoder.stocks.domain.rate.StockRate
import ru.kcoder.stocks.domain.rate.StockRateLive
import ru.kcoder.stocks.domain.rate.StockRateRepository
import timber.log.Timber
import javax.inject.Inject

class StockRateRepositoryImpl @Inject constructor(
    private val stockRateDataStore: StockRateDataStore,
    private val stockRateMapper: StockRate.Mapper,
    private val stockRateLiveMapper: StockRateLive.Mapper,
) : StockRateRepository {

    override fun getStockRate(
        selectedStockId: String
    ): Single<StockRate> {
        return stockRateDataStore.getStockRate(selectedStockId).map { dto ->
            stockRateMapper.map(dto, selectedStockId)
        }
    }

    override fun getStockRateStream(
        selectedStockId: String,
        previousStockId: String?
    ): Observable<StockRateLive> {
        Timber.d("@@@@@12334444")
        return stockRateDataStore.getStockRateStream(selectedStockId, previousStockId)
            .filter { dto ->
                Timber.d("@@@@@12334444")
                dto.type == StockRateStreamType.TRADING_QUOTE.description
                        && dto.body?.securityId == selectedStockId
            }
            .map(stockRateLiveMapper::map)
    }
}