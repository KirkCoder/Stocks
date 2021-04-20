package ru.kcoder.stocks.domain.rate

import io.reactivex.Observable
import ru.kcoder.stocks.domain.stock.Stock
import ru.kcoder.stocks.domain.stock.StocksRepository
import javax.inject.Inject

class ObserveSelectedStockUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {

    fun execute(): Observable<Stock> {
        return stocksRepository.observeSelectedStock()
    }
}