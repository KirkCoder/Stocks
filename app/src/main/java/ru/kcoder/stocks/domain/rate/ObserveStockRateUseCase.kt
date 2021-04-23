package ru.kcoder.stocks.domain.rate

import io.reactivex.Observable
import javax.inject.Inject

class ObserveStockRateUseCase @Inject constructor(
    private val stockRateRepository: StockRateRepository,
    private val stockRateMapper: StockRate.Mapper
) {

    fun execute(selectedStockId: String, previousStockId: String?): Observable<StockRate> {
        return stockRateRepository.getStockRate(selectedStockId).toObservable()
            .flatMap { stockRate ->
                stockRateRepository.getStockRateStream(selectedStockId, previousStockId)
                    .map { stockRateLive ->
                        stockRateMapper.map(stockRate, stockRateLive)
                    }
                    .startWith(stockRate)
            }
    }

}