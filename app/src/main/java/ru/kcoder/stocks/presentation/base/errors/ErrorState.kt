package ru.kcoder.stocks.presentation.base.errors

data class ErrorState(
    val error: ErrorDescription,
    val retryAction: () -> Unit,
    val cancelAction: () -> Unit
)