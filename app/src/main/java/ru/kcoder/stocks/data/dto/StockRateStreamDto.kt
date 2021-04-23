package ru.kcoder.stocks.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class StockRateStreamDto(
    @SerializedName("t") val type: String?,
    @SerializedName("body") val body: StockRateStreamBodyDto?
) {

    class Mapper @Inject constructor(
        private val gson: Gson
    ) {
        fun map(response: String): StockRateStreamDto {
            return gson.fromJson<StockRateStreamDto>(response, StockRateStreamDto::class.java)
        }
    }
}

data class StockRateStreamBodyDto(
    @SerializedName("securityId") val securityId: String?,
    @SerializedName("currentPrice") val currentPrice: String?,
    @SerializedName("developerMessage") val developerMessage: String?,
    @SerializedName("errorCode") val errorCode: String?,
)

enum class StockRateStreamType(val description: String) { //we do not need enum for one type but I think in real production it will be a lot of types
    TRADING_QUOTE("trading.quote")
}