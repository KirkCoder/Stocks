package ru.kcoder.stocks.presentation.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
            amountInputEditText.hideKeyboard()
            findNavController().navigate(R.id.action_RatesFragment_to_AllStocksFragment)
        }

        amountInputEditText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            text?.let {
                viewModel.calculateChanges(text.toString())
            }
        })

        retryButton.setOnClickListener {
            calculateExistingExchange()
        }

        closeButton.setOnClickListener {
            viewModel.close()
        }
    }

    private fun subscribeUi() {
        observe(viewModel.selectedStockLiveData, ::setSelectedStock)
        observe(viewModel.stockExchangeLiveData, ::handleStockExchange)
        observe(viewModel.stockRateLiveData, ::handleStockRate)
        observe(viewModel.closeLiveData, ::close)
    }

    private fun setSelectedStock(currency: SelectedStockPresentation) {
        selectedStockButton.text = currency.name
    }

    private fun calculateExistingExchange() {
        viewModel.calculateChanges(amountInputEditText.text?.toString() ?: "")
    }

    private fun handleStockExchange(
        stockExchangePresentation: StockExchangePresentation
    ) {
        progressBar.isVisible = stockExchangePresentation.showProgress
        errorLayout.isVisible = stockExchangePresentation.showError
    }

    private fun handleStockRate(stock: StockRatePresentation) {
        currentPriceTextView.text = stock.currentPrice
        closePriceTextView.text = stock.closePrice
        differenceTextView.text = stock.difference
    }

    private fun close(any: Any) {
        addCompatActivity?.onBackPressed()
    }
}
