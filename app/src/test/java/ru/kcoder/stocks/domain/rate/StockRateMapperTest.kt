package ru.kcoder.stocks.domain.rate

import com.nhaarman.mockitokotlin2.any
import org.junit.Assert.*
import org.junit.Test
import ru.kcoder.stocks.data.dto.PriceDto
import ru.kcoder.stocks.data.dto.StockRateDto
import ru.kcoder.stocks.data.network.StocksError
import java.math.BigDecimal
import java.math.RoundingMode

class StockRateMapperTest {

    private val mapper = StockRate.Mapper()

    private val selectedStockId = "123"

    private val stockRateDto = StockRateDto(
        symbol = null,
        securityId = null,
        message = null,
        developerMessage = null,
        errorCode = null,
        displayName = null,
        currentPrice = null,
        closingPrice = null
    )

    private val priceDto = PriceDto(
        currency = "USD",
        decimals = null,
        amount = "10"
    )

    @Test
    fun `Check dto with error code mapping`() {
        assertThrows(StocksError.Server::class.java) {
            mapper.map(stockRateDto.copy(errorCode = "1"), selectedStockId)
        }
    }

    @Test
    fun `Check dto mapping`() {
        checkMapping("15", ::getDifference, ::assertEquals)
    }

    @Test
    fun `Check dto mapping big difference`() {
        checkMapping("150.34", ::getDifference, ::assertEquals)
    }

    @Test
    fun `Check dto wrong diff calculation`() {
        checkMapping("150.34", { _, _ ->
            BigDecimal(1)
        }, ::assertNotEquals)
    }

    private fun checkMapping(
        amount: String,
        diffCalculator: (BigDecimal, BigDecimal) -> BigDecimal,
        asserter: (StockRate, StockRate) -> Unit
    ) {
        val currentPriceDto = priceDto.copy(
            amount = amount
        )
        val stockRate = mapper.map(
            stockRateDto.copy(
                currentPrice = currentPriceDto,
                closingPrice = priceDto,
            ),
            selectedStockId
        )
        val currentPrice = BigDecimal(amount)
        val closePrice = BigDecimal(10)
        val expectedStockRate = StockRate(
            id = selectedStockId,
            currency = priceDto.currency!!,
            currentPrice = currentPrice,
            closePrice = closePrice,
            difference = diffCalculator(currentPrice, closePrice)
        )
        asserter(expectedStockRate, stockRate)
    }

    private fun getDifference(currentPrice: BigDecimal, closePrice: BigDecimal): BigDecimal {
        val onePercent = closePrice.divide(BigDecimal(100), 6, RoundingMode.HALF_UP)
        val currentInPercents =
            currentPrice.divide(onePercent, 6, RoundingMode.HALF_UP)
        return currentInPercents.minus(BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)
    }
}