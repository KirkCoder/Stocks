package ru.kcoder.stocks.presentation.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.stocks.config.RxSchedulers
import ru.kcoder.stocks.domain.stock.GetAllStocksUseCase
import ru.kcoder.stocks.domain.stock.SelectStockUseCase
import ru.kcoder.stocks.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class StockViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val getAllStocksUseCase: GetAllStocksUseCase,
    private val selectStockUseCase: SelectStockUseCase,
    private val stocksPresentationFormatter: StockPresentation.Formatter
) : BaseViewModel(rxSchedulers) {

    val allStocksLiveData: LiveData<AllStocksPresentation>
        get() = _allStocksLiveData

    private val _allStocksLiveData = MutableLiveData<AllStocksPresentation>()

    val closeLiveData: LiveData<Any>
        get() = _closeLiveData

    private val _closeLiveData = MutableLiveData<Any>()

    init {
        loadAllStocks()
    }

    fun loadAllStocks() {
        getAllStocksUseCase.execute()
            .map(stocksPresentationFormatter::format)
            .schedule(
                onSuccess = { stocks ->
                    showStocks(stocks)
                },
                onSubscribe = {
                    showProgress()
                },
                onError = { error ->
                    showError()
                    Timber.e(error)
                }
            )
    }

    private fun showStocks(stocks: List<StockPresentation>) {
        _allStocksLiveData.value = AllStocksPresentation(
            stocks = stocks,
            showProgress = false,
            showError = false
        )
    }

    private fun showProgress() {
        _allStocksLiveData.value = AllStocksPresentation(
            stocks = emptyList(),
            showProgress = true,
            showError = false
        )
    }

    private fun showError() {
        _allStocksLiveData.value = AllStocksPresentation(
            stocks = emptyList(),
            showProgress = false,
            showError = true
        )
    }

    fun selectStock(stockPresentation: StockPresentation) {
        selectStockUseCase.execute(stockPresentation.id)
            .schedule(
                onComplete = {
                    close()
                },
                onError = Timber::e
            )
    }

    fun close() {
        _closeLiveData.value = Any()
    }
}