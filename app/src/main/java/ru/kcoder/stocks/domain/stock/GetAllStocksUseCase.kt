package ru.kcoder.stocks.domain.stock

import io.reactivex.Single
import javax.inject.Inject

class GetAllStocksUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {

    fun execute(): Single<List<Stock>> {
        return stocksRepository.getAllStocks()
    }
}