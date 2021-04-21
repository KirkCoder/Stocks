package ru.kcoder.stocks.data.dto

import com.google.gson.annotations.SerializedName

data class StockRateStreamDto(
    @SerializedName("t") val type: String?,
    @SerializedName("body") val body: StockRateStreamBodyDto?
)

data class StockRateStreamBodyDto(
    @SerializedName("securityId") val securityId: String?,
    @SerializedName("currentPrice") val currentPrice: String?,
)