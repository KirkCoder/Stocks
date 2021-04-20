package ru.kcoder.stocks.data.dto

import com.google.gson.annotations.SerializedName

data class StockRateDto(
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("securityId") val securityId: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("currentPrice") val currentPrice: PriceDto?,
    @SerializedName("closingPrice") val closingPrice: PriceDto?,
)

data class PriceDto(
    @SerializedName("currency") val currency: String?,
    @SerializedName("decimals") val decimals: String?,
    @SerializedName("amount") val amount: String?,
)