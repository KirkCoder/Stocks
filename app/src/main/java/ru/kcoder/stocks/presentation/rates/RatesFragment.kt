package ru.kcoder.stocks.presentation.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_rates.*
import ru.kcoder.stocks.App
import ru.kcoder.stocks.R
import ru.kcoder.stocks.presentation.base.BaseFragment
import ru.kcoder.stocks.presentation.base.ViewModelFactory
import javax.inject.Inject

class RatesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RatesViewModel by lazy(LazyThreadSafetyMode.NONE) {
        initViewModel<RatesViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }

    override fun initDI() {
        super.initDI()
        App.instance.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        initListeners()
    }

    private fun initListeners() {
        selectedStockButton.setOnClickListener {
            findNavController().navigate(R.id.action_RatesFragment_to_AllStocksFragment)
        }
    }

    private fun subscribeUi() {
        observe(viewModel.stockLiveData, ::handleStock)
        observe(viewModel.closeLiveData, ::close)
    }

    private fun handleStock(stock: StockPresentation) {
        setProgress(stock)
        setError(stock)
        setSelectedStock(stock.selectedStock)
        setStockRate(stock.stockRate)
    }

    private fun setProgress(stock: StockPresentation) {
        progressBar.isVisible = stock.showProgress
    }

    private fun setError(stock: StockPresentation) {
        errorLayout.isVisible = stock.errorState != null
        errorDescriptionTextView.text = stock.errorState?.error?.description
        retryButton.setOnClickListener {
            stock.errorState?.retryAction?.invoke()
        }
        closeButton.setOnClickListener {
            stock.errorState?.cancelAction?.invoke()
        }
    }

    private fun setSelectedStock(stock: SelectedStockPresentation?) {
        selectedStockButton.text = stock?.name
    }

    private fun setStockRate(stock: StockRatePresentation?) {
        currentPriceTextView.text = stock?.currentPrice
        closePriceTextView.text = stock?.closePrice
        differenceTextView.text = stock?.difference
    }

    private fun close(any: Any) {
        addCompatActivity?.onBackPressed()
    }
}
