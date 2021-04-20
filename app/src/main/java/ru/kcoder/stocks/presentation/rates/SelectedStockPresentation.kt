package ru.kcoder.stocks.presentation.rates

import ru.kcoder.stocks.R
import ru.kcoder.stocks.domain.stock.Stock
import ru.kcoder.stocks.presentation.base.ResourceDataStore
import javax.inject.Inject

data class SelectedStockPresentation(
    val id: String,
    val name: String,
) {
    class Formatter @Inject constructor(
        private val resourceDataStore: ResourceDataStore,
    ) {
        fun format(selectedStock: Stock): SelectedStockPresentation {
            return SelectedStockPresentation(
                id = selectedStock.id,
                name = resourceDataStore.getString(R.string.selected_stock, selectedStock.name),
            )
        }
    }
}