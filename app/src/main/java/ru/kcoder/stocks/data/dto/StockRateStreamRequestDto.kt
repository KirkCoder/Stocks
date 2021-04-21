package ru.kcoder.stocks.data.dto

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class StockRateStreamRequestDto(
    @SerializedName("subscribeTo") val subscribeTo: List<String>?,
    @SerializedName("unsubscribeFrom") val unsubscribeFrom: List<String>?,
) {

    class Mapper @Inject constructor() {
        fun map(id: String) {
            StockRateStreamRequestDto(
                subscribeTo = listOf("trading.product.${id}"),
                unsubscribeFrom = emptyList()
            )
        }
    }
}