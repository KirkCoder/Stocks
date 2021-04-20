package ru.kcoder.stocks.presentation.stock

import ru.kcoder.stocks.domain.stock.Stock
import javax.inject.Inject

data class StockPresentation(
    val id: String,
    val name: String
) {
    class Formatter @Inject constructor() {
        fun format(stocks: List<Stock>): List<StockPresentation> {
            return stocks.map { (id, name) ->
                StockPresentation(
                    id = id,
                    name = name,
                )
            }
        }
    }
}