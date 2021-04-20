package ru.kcoder.stocks.presentation.stock

data class AllStocksPresentation(
    val showError: Boolean,
    val showProgress: Boolean,
    val stocks: List<StockPresentation>
)