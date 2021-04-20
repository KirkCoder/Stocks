package ru.kcoder.stocks.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.kcoder.stocks.data.dto.StockRateDto

interface StocksApi {

    @GET("core/23/products/{productId}")
    fun getStockRate(
        @Path("productId") productId: String
    ): Single<StockRateDto>
}