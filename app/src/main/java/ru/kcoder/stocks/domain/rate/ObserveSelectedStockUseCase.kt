package ru.kcoder.stocks.domain.rate

import io.reactivex.Observable
import io.reactivex.Single
import ru.kcoder.stocks.domain.stock.Stock
import ru.kcoder.stocks.domain.stock.StocksRepository
import javax.inject.Inject

class ObserveSelectedStockUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {

    fun execute(): Observable<Stock> {
        return stocksRepository.getSelectedStock().switchIfEmpty(
            selectDefaultStock()
        ).flatMapObservable {
            stocksRepository.observeSelectedStock()
        }
    }

    private fun selectDefaultStock(): Single<Stock> {
        return stocksRepository.getAllStocks().flatMap { stocks ->
            val selectedStock = stocks.first()
            stocksRepository.selectStock(selectedStock.id)
                .toSingle { selectedStock }
        }
    }
}