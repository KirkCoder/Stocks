package ru.kcoder.stocks.presentation.rates

import dagger.Reusable
import ru.kcoder.stocks.R
import ru.kcoder.stocks.domain.rate.StockRate
import ru.kcoder.stocks.presentation.base.ResourceDataStore
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

data class StockRatePresentation(
    val currentPrice: String,
    val closePrice: String,
    val difference: String,
) {

    @Reusable
    class Formatter @Inject constructor(
        private val resourceDataStore: ResourceDataStore,
    ) {

        fun format(stockRate: StockRate): StockRatePresentation {
            val currencyFormatter = NumberFormat.getCurrencyInstance()
            currencyFormatter.currency = Currency.getInstance(stockRate.currency)
            return StockRatePresentation(
                currentPrice = resourceDataStore.getString(R.string.current_price, formatPrice(stockRate.currentPrice, currencyFormatter)),
                closePrice = resourceDataStore.getString(R.string.close_price, formatPrice(stockRate.closePrice, currencyFormatter)),
                difference =
                resourceDataStore.getString(
                    R.string.difference,
                    stockRate.difference.toString(),
                ) +
                        resourceDataStore.getString(
                            R.string.percent,
                        ),
            )
        }

        private fun formatPrice(price: BigDecimal, formatter: NumberFormat): String {
            return formatter.format(price)
        }
    }
}