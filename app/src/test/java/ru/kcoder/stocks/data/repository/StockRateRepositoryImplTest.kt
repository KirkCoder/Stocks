package ru.kcoder.stocks.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import ru.kcoder.stocks.data.dto.StockRateStreamBodyDto
import ru.kcoder.stocks.data.dto.StockRateStreamDto
import ru.kcoder.stocks.data.dto.StockRateStreamType
import ru.kcoder.stocks.data.storage.StockRateDataStore
import ru.kcoder.stocks.domain.rate.StockRate
import ru.kcoder.stocks.domain.rate.StockRateLive
import java.math.BigDecimal

class StockRateRepositoryImplTest {
    private val stockRateDataStore = mock<StockRateDataStore>()
    private val stockRateMapper = mock<StockRate.Mapper>()
    private val stockRateLiveMapper = mock<StockRateLive.Mapper>()
    private val stockRateRepository =
        StockRateRepositoryImpl(
            stockRateDataStore = stockRateDataStore,
            stockRateMapper = stockRateMapper,
            stockRateLiveMapper = stockRateLiveMapper
        )

    private val selectedStockId = "123"

    private val stockRateStreamDto = StockRateStreamDto(
        type = StockRateStreamType.TRADING_QUOTE.description,
        body = StockRateStreamBodyDto(
            securityId = selectedStockId,
            currentPrice = "10",
            developerMessage = null,
            errorCode = null,
        )
    )

    private val stockRateLive = StockRateLive(
        id = "id",
        price = BigDecimal(10)
    )

    @Before
    fun init() {
        whenever(stockRateLiveMapper.map(any())).thenReturn(stockRateLive)
    }

    @Test
    fun `Filter wrong stock rate`() {
        val dto = stockRateStreamDto.copy(body = stockRateStreamDto.body?.copy(securityId = "1"))
        whenever(
            stockRateDataStore.getStockRateStream(
                selectedStockId,
                null
            )
        ).thenReturn(Observable.just(dto))
        stockRateRepository.getStockRateStream(selectedStockId, null).test()
            .assertNoValues()
    }

    @Test
    fun `Correct stock rate stream answer`() {
        whenever(
            stockRateDataStore.getStockRateStream(
                selectedStockId,
                null
            )
        ).thenReturn(Observable.just(stockRateStreamDto))
        stockRateRepository.getStockRateStream(selectedStockId, null).test()
            .assertValue(stockRateLive)
    }
}