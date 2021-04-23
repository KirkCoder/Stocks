package ru.kcoder.stocks.presentation.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import ru.kcoder.stocks.config.RxSchedulers
import ru.kcoder.stocks.domain.rate.ObserveSelectedStockUseCase
import ru.kcoder.stocks.domain.rate.ObserveStockRateUseCase
import ru.kcoder.stocks.presentation.base.BaseViewModel
import ru.kcoder.stocks.presentation.base.errors.ErrorDescription
import ru.kcoder.stocks.presentation.base.errors.ErrorState
import timber.log.Timber
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val observeSelectedStockUseCase: ObserveSelectedStockUseCase,
    private val observeStockRateUseCase: ObserveStockRateUseCase,
    private val stockRatePresentationFormatter: StockRatePresentation.Formatter,
    private val stockFormatter: SelectedStockPresentation.Formatter,
    private val errorDescriptionFormatter: ErrorDescription.Formatter,
) : BaseViewModel(rxSchedulers) {

    private var currentStockId: String? = null
    private var stockRateDisposable: Disposable? = null

    val closeLiveData: LiveData<Any>
        get() = _closeLiveData

    private val _closeLiveData = MutableLiveData<Any>()

    val stockLiveData: LiveData<StockPresentation>
        get() = _stockLiveData

    private val _stockLiveData = MutableLiveData<StockPresentation>()

    init {
        observeSelectedStock()
    }

    private fun close() {
        _closeLiveData.value = Any()
    }

    private fun observeSelectedStock() {
        observeSelectedStockUseCase.execute()
            .map(stockFormatter::format)
            .schedule(
                onSubscribe = {
                    showProgress()
                },
                onNext = { selectedStockPresentation ->
                    observeStockRate(selectedStockPresentation)
                },
                onError = { error ->
                    showSelectedStockError(error)
                    Timber.e(error)
                }
            )
    }

    private fun showProgress() {
        _stockLiveData.value = StockPresentation(
            showProgress = true
        )
    }

    private fun showSelectedStockError(error: Throwable) {
        _stockLiveData.value = StockPresentation(
            showProgress = false,
            errorState = ErrorState(
                error = errorDescriptionFormatter.format(error),
                retryAction = ::observeSelectedStock,
                cancelAction = ::close
            )
        )
    }

    private fun observeStockRate(selectedStockPresentation: SelectedStockPresentation) {
        stockRateDisposable?.dispose()
        observeStockRateUseCase.execute(selectedStockPresentation.id, currentStockId)
            .map(stockRatePresentationFormatter::format)
            .schedule(
                onSubscribe = { disposable ->
                    stockRateDisposable = disposable
                    showProgress()
                },
                onNext = { stockRatePresentation ->
                    currentStockId = selectedStockPresentation.id
                    showStock(stockRatePresentation, selectedStockPresentation)
                },
                onError = { error ->
                    showStockRateError(selectedStockPresentation, error)
                    Timber.e(error)
                }
            )
    }

    private fun showStock(
        stockRatePresentation: StockRatePresentation,
        selectedStockPresentation: SelectedStockPresentation
    ) {
        _stockLiveData.value = StockPresentation(
            showProgress = false,
            selectedStock = selectedStockPresentation,
            stockRate = stockRatePresentation,
        )
    }

    private fun showStockRateError(
        selectedStockPresentation: SelectedStockPresentation,
        error: Throwable
    ) {
        _stockLiveData.value = StockPresentation(
            showProgress = false,
            errorState = ErrorState(
                error = errorDescriptionFormatter.format(error),
                retryAction = {
                    observeStockRate(selectedStockPresentation)
                },
                cancelAction = ::close
            )
        )
    }
}