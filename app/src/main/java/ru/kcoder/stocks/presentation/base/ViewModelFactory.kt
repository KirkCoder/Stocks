package ru.kcoder.stocks.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kcoder.stocks.presentation.rates.RatesViewModel
import ru.kcoder.stocks.presentation.stock.StockViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val ratesViewModelProvider: Provider<RatesViewModel>,
    private val stockViewModelProvider: Provider<StockViewModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RatesViewModel::class.java) -> ratesViewModelProvider.get() as T
            modelClass.isAssignableFrom(StockViewModel::class.java) -> stockViewModelProvider.get() as T
            else -> throw Throwable("View model of class $modelClass is not supported by factory")
        }
    }
}