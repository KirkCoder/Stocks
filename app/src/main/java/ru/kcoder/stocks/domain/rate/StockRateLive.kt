package ru.kcoder.stocks.domain.rate

import ru.kcoder.stocks.data.dto.StockRateStreamBodyDto
import ru.kcoder.stocks.data.dto.StockRateStreamDto
import ru.kcoder.stocks.data.network.ServerError
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

data class StockRateLive(
    val id: String,
    val price: BigDecimal
) {

    class Mapper @Inject constructor() {

        fun map(dto: StockRateStreamDto): StockRateLive {
            if (dto.body?.errorCode != null) {
                Timber.d("ServerError code: ${dto.body.errorCode}, developerMessage: ${dto.body.developerMessage}")
                throw ServerError(null)
            }
            return StockRateLive(
                id = getId(dto),
                price = getCurrentPrice(dto.body)
            )
        }

        private fun getId(dto: StockRateStreamDto) =
            dto.body?.securityId
                ?: throw IllegalArgumentException("Security id in StockRateStreamDto invalid")

        private fun getCurrentPrice(body: StockRateStreamBodyDto?) =
            body?.currentPrice?.toBigDecimalOrNull()
                ?: throw IllegalArgumentException("Current price in StockRateStreamDto invalid price: ${body?.currentPrice}, id: ${body?.securityId}")
    }
}