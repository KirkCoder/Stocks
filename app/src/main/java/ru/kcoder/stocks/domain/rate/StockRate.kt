package ru.kcoder.stocks.domain.rate

import ru.kcoder.stocks.data.dto.PriceDto
import ru.kcoder.stocks.data.dto.StockRateDto
import ru.kcoder.stocks.data.network.StocksError
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

data class StockRate(
    val id: String,
    val currency: String,
    val closePrice: BigDecimal,
    val currentPrice: BigDecimal,
    val difference: BigDecimal,
) {

    class Mapper @Inject constructor() {
        fun map(dto: StockRateDto, id: String): StockRate {
            if (dto.errorCode != null) {
                Timber.d("ServerError code: ${dto.errorCode}, developerMessage: ${dto.developerMessage}, message: ${dto.message}")
                throw StocksError.Server(dto.message)
            }

            val currentPriceDto = getCurrentPrice(dto, id)
            val closingPriceDto = getClosePrice(dto, id)
            val currentPrice = getCurrentPrice(currentPriceDto, id)
            val closePrice = getClosePrice(closingPriceDto, id)
            val diff = getDifference(currentPrice, closePrice)
            return StockRate(
                id = id,
                currency = getCurrency(currentPriceDto, id),
                currentPrice = currentPrice,
                closePrice = closePrice,
                difference = diff
            )
        }

        fun map(stockRate: StockRate, stockRateLive: StockRateLive): StockRate {
            val diff = getDifference(stockRateLive.price, stockRate.closePrice)
            return stockRate.copy(
                currentPrice = stockRateLive.price,
                difference = diff
            )
        }

        private fun getCurrentPrice(
            dto: StockRateDto,
            id: String
        ) = (dto.currentPrice
            ?: throw IllegalAccessException("current price must not be null in stock id:$id"))

        private fun getClosePrice(
            dto: StockRateDto,
            id: String
        ) = (dto.closingPrice
            ?: throw IllegalAccessException("closing price must not be null in stock id:$id"))

        private fun getCurrency(
            currentPrice: PriceDto,
            id: String
        ) = (currentPrice.currency
            ?: throw IllegalAccessException("currency in current price must not be null in stock id:$id"))

        private fun getCurrentPrice(
            currentPrice: PriceDto,
            id: String
        ) = (currentPrice.amount?.toBigDecimalOrNull()
            ?: throw IllegalAccessException("invalid amount in current price id:$id"))

        private fun getClosePrice(
            closingPrice: PriceDto,
            id: String
        ) = (closingPrice.amount?.toBigDecimalOrNull()
            ?: throw IllegalAccessException("invalid amount in current price id:$id"))

        private fun getDifference(currentPrice: BigDecimal, closePrice: BigDecimal): BigDecimal {
            val onePercent = closePrice.divide(ONE_HUNDRED, SCALE_MODE, RoundingMode.HALF_UP)
            val currentInPercents =
                currentPrice.divide(onePercent, SCALE_MODE, RoundingMode.HALF_UP)
            return currentInPercents.minus(ONE_HUNDRED).setScale(PRESENTATION_SCALE_MODE, RoundingMode.HALF_UP)
        }
    }

    companion object {
        private val ONE_HUNDRED = BigDecimal(100)
        private const val SCALE_MODE = 6
        private const val PRESENTATION_SCALE_MODE = 2
    }
}