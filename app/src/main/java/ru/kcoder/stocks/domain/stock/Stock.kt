package ru.kcoder.stocks.domain.stock

import ru.kcoder.stocks.data.dto.StockDto
import ru.kcoder.stocks.data.persistent.StockEntity
import timber.log.Timber
import javax.inject.Inject

data class Stock(
    val id: String,
    val name: String
) {

    class Mapper @Inject constructor() {
        fun map(stocks: List<StockDto>): List<Stock> {
            return stocks.mapNotNull { dto ->
                with(dto) {
                    if (name != null && id != null) {
                        Stock(
                            id = id,
                            name = name
                        )
                    } else {
                        Timber.d("wrong data in stock dto name: ${dto.name}, id: ${dto.id}")
                        null
                    }
                }
            }
        }

        fun map(stock: StockEntity): Stock {
            return Stock(
                id = stock.id,
                name = stock.name
            )
        }
    }

}