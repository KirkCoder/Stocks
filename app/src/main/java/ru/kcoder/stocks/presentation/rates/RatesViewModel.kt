package ru.kcoder.stocks.presentation.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.stocks.config.RxSchedulers
import ru.kcoder.stocks.domain.rate.ObserveSelectedStockUseCase
import ru.kcoder.stocks.domain.rate.ObserveStockRateUseCase
import ru.kcoder.stocks.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val observeSelectedStockUseCase: ObserveSelectedStockUseCase,
    private val observeStockRateUseCase: ObserveStockRateUseCase,
    private val stockRatePresentationFormatter: StockRatePresentation.Formatter,
    private val stockFormatter: SelectedStockPresentation.Formatter,
) : BaseViewModel(rxSchedulers) {

    val closeLiveData: LiveData<Any>
        get() = _closeLiveData

    private val _closeLiveData = MutableLiveData<Any>()

    val selectedStockLiveData: LiveData<SelectedStockPresentation>
        get() = _selectedStockLiveData

    private val _selectedStockLiveData = MutableLiveData<SelectedStockPresentation>()

    val stockExchangeLiveData: LiveData<StockExchangePresentation>
        get() = _currencyExchangeLiveData

    private val _currencyExchangeLiveData = MutableLiveData<StockExchangePresentation>()

    val stockRateLiveData: LiveData<StockRatePresentation>
        get() = _stockRateLiveData

    private val _stockRateLiveData = MutableLiveData<StockRatePresentation>()

    init {
        observeSelectedStock()
        observeStockRate()
    }

    fun close() {
        _closeLiveData.value = Any()
    }

    fun calculateChanges(amount: String) {

    }

    private fun observeSelectedStock() {
        observeSelectedStockUseCase.execute()
            .map(stockFormatter::format)
            .schedule(
                onNext = { selectedStockPresentation ->
                    _selectedStockLiveData.value = selectedStockPresentation
                },
                onError = Timber::e
            )
    }

    private fun observeStockRate() {
        observeStockRateUseCase.execute()
            .map(stockRatePresentationFormatter::format)
            .schedule(
                onNext = {
                    _stockRateLiveData.value = it
                },
                onError = Timber::e
            )
    }
}