package ru.kcoder.stocks.presentation.rates

import ru.kcoder.stocks.domain.rate.StockRate
import javax.inject.Inject

data class StockRatePresentation(
    val currentPrice: String,
    val closePrice: String,
    val difference: String,
) {

    class Formatter @Inject constructor() {
        fun format(stockRate: StockRate): StockRatePresentation {
            return StockRatePresentation(
                currentPrice = stockRate.currentPrice.toString(),
                closePrice = stockRate.closePrice.toString(),
                difference = stockRate.currency,
            )
        }
    }
}