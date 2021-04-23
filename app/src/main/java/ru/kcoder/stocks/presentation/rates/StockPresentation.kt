package ru.kcoder.stocks.presentation.rates

import ru.kcoder.stocks.presentation.base.errors.ErrorState

data class StockPresentation(
    val showProgress: Boolean,
    val selectedStock: SelectedStockPresentation? = null,
    val stockRate: StockRatePresentation? = null,
    val errorState: ErrorState? = null,
)