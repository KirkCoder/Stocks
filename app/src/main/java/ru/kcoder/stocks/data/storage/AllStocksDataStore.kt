package ru.kcoder.stocks.data.storage

import io.reactivex.Single
import ru.kcoder.stocks.data.dto.StockDto
import java.lang.IllegalStateException
import javax.inject.Inject

class AllStocksDataStore @Inject constructor() {
    fun getAllStocks(): Single<List<StockDto>> {
        return Single.fromCallable {
            ALL_STOCKS
        }
    }

    fun getStockById(id: String): Single<StockDto> {
        return Single.fromCallable {
            val stock = ALL_STOCKS.firstOrNull { dto -> dto.id == id }
                ?: throw IllegalStateException("can't find this type of stock, with id: $id")
            stock
        }
    }

    companion object {
        private val ALL_STOCKS = listOf(
            StockDto(
                id = "sb26493",
                name = "Germany30",
            ),
            StockDto(
                id = "sb26496",
                name = "US500",
            ),
            StockDto(
                id = "sb26502",
                name = "EUR/USD",
            ),
            StockDto(
                id = "sb26500",
                name = "Gold",
            ),
            StockDto(
                id = "sb26513",
                name = "Apple",
            ),
            StockDto(
                id = "sb28248",
                name = "Deutsche Bank",
            ),
        )
    }
}