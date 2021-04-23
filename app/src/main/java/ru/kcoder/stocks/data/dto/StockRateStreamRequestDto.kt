package ru.kcoder.stocks.data.dto

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class StockRateStreamRequestDto(
    @SerializedName("subscribeTo") val subscribeTo: List<String>?,
    @SerializedName("unsubscribeFrom") val unsubscribeFrom: List<String>?,
) {

    class Mapper @Inject constructor() {
        fun map(id: String, previousStockId: String?): StockRateStreamRequestDto {
            val unsubscribeList =
                previousStockId?.let { listOf("$SUBSCRIPTION_PARAM_NAME${it}") } ?: emptyList()
            return StockRateStreamRequestDto(
                subscribeTo = listOf("$SUBSCRIPTION_PARAM_NAME${id}"),
                unsubscribeFrom = unsubscribeList
            )
        }
    }

    companion object {
        private const val SUBSCRIPTION_PARAM_NAME = "trading.product."
    }
}