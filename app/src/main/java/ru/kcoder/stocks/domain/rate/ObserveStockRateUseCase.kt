package ru.kcoder.stocks.domain.rate

import io.reactivex.Observable
import javax.inject.Inject

class ObserveStockRateUseCase @Inject constructor(
    private val stockRateRepository: StockRateRepository,
    private val observeSelectedStockUseCase: ObserveSelectedStockUseCase,
) {

    fun execute(): Observable<StockRate> {
        return observeSelectedStockUseCase.execute().flatMap { stock ->
            stockRateRepository.getStockRate(stock.id).toObservable()
        }
    }

}