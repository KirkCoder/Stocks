package ru.kcoder.stocks.domain.rate

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ObserveStockRateUseCaseTest {
    private val stockRateRepository = mock<StockRateRepository>()
    private val stockRateMapper = mock<StockRate.Mapper>()
    private val observeStockRateUseCase = ObserveStockRateUseCase(
        stockRateRepository = stockRateRepository,
        stockRateMapper = stockRateMapper,
    )

    private val selectedStockId = "123"
    private val previousStockId = null

    private val stockRate = StockRate(
        id = "id",
        currency = "USD",
        closePrice = BigDecimal(10),
        currentPrice = BigDecimal(15),
        difference = BigDecimal(50)
    )

    private val stockRateLive = StockRateLive(
        id = "id",
        price = BigDecimal(15),
    )

    @Before
    fun init() {
        whenever(stockRateRepository.getStockRate(any())).thenReturn(Single.just(stockRate))
        whenever(stockRateMapper.map(any(), any<StockRateLive>())).thenReturn(stockRate)
    }

    @Test
    fun `Check correct response before stream ready`() {
        whenever(
            stockRateRepository.getStockRateStream(
                selectedStockId,
                previousStockId
            )
        ).thenReturn(Observable.empty())

        observeStockRateUseCase.execute(selectedStockId, previousStockId).test()
            .assertValue(stockRate)
    }

    @Test
    fun `Check correct stream response`() {
        whenever(
            stockRateRepository.getStockRateStream(
                selectedStockId,
                previousStockId
            )
        ).thenReturn(Observable.just(stockRateLive))

        observeStockRateUseCase.execute(selectedStockId, previousStockId).test()
            .assertValues(stockRate, stockRate)
    }

}