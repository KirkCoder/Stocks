package ru.kcoder.stocks.domain.stock

import io.reactivex.Completable
import javax.inject.Inject

class SelectStockUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {

    fun execute(id: String): Completable {
        return stocksRepository.selectStock(id)
    }
}