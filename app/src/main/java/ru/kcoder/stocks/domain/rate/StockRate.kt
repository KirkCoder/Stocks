package ru.kcoder.stocks.domain.rate

import ru.kcoder.stocks.data.dto.StockRateDto
import java.math.BigDecimal
import javax.inject.Inject

data class StockRate(
    val id: String,
    val currency: String,
    val closePrice: BigDecimal,
    val currentPrice: BigDecimal,
) {

    class Mapper @Inject constructor() {
        fun map(dto: StockRateDto, id: String): StockRate {
            val currentPrice = dto.currentPrice
                ?: throw IllegalAccessException("current price must not be null in stock id:$id")
            val closingPrice = dto.closingPrice
                ?: throw IllegalAccessException("closing price must not be null in stock id:$id")
            return StockRate(
                id = id,
                currency = dto.currentPrice.currency
                    ?: throw IllegalAccessException("currency in current price must not be null in stock id:$id"),
                currentPrice = currentPrice.amount?.toBigDecimalOrNull()
                    ?: throw IllegalAccessException("invalid amount in current price id:$id"),
                closePrice = closingPrice.amount?.toBigDecimalOrNull()
                    ?: throw IllegalAccessException("invalid amount in current price id:$id"),
            )
        }
    }
}