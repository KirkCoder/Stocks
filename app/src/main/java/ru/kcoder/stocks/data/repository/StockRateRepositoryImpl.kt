package ru.kcoder.stocks.data.repository

import io.reactivex.Single
import ru.kcoder.stocks.data.storage.StockRateDataStore
import ru.kcoder.stocks.domain.rate.StockRate
import ru.kcoder.stocks.domain.rate.StockRateRepository
import javax.inject.Inject

class StockRateRepositoryImpl @Inject constructor(
    private val stockRateDataStore: StockRateDataStore,
    private val stockRateMapper: StockRate.Mapper,
) : StockRateRepository {

    override fun getStockRate(id: String): Single<StockRate> {
        return stockRateDataStore.getStockRate(id).map { dto ->
            stockRateMapper.map(dto, id)
        }
    }
}