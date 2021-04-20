package ru.kcoder.stocks.data.dto

import com.google.gson.annotations.SerializedName

data class StockDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
)