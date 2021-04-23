package ru.kcoder.stocks.data.storage

import com.google.gson.Gson
import ru.kcoder.stocks.data.dto.StockRateStreamRequestDto
import javax.inject.Inject

class StockRateStreamRequestMapper @Inject constructor(
    private val gson: Gson,
    private val stockRateStreamRequestDtoMapper: StockRateStreamRequestDto.Mapper,
) {

    fun getRequest(selectedStockId: String, previousStockId: String?): String {
        return gson.toJson(stockRateStreamRequestDtoMapper.map(selectedStockId, previousStockId))
    }
}