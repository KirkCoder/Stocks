package ru.kcoder.stocks.presentation.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_stock.*
import ru.kcoder.stocks.App
import ru.kcoder.stocks.R
import ru.kcoder.stocks.presentation.base.BaseFragment
import ru.kcoder.stocks.presentation.base.ViewModelFactory
import javax.inject.Inject

class StockFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: StockViewModel by lazy(LazyThreadSafetyMode.NONE) {
        initViewModel<StockViewModel>(viewModelFactory)
    }

    private val stocksAdapter = StockAdapter(::onSelectStock)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun initDI() {
        super.initDI()
        App.instance.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initListeners()
        subscribeUi()
    }

    private fun initRecycler() {
        with(allStocksRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = stocksAdapter
            addItemDecoration(
                StockItemDecorator(
                    marginBottom = context.resources.getDimension(R.dimen.side_margin).toInt()
                )
            )
        }
    }

    private fun initListeners() {
        retryButton.setOnClickListener {
            viewModel.loadAllStocks()
        }

        closeButton.setOnClickListener {
            viewModel.close()
        }
    }

    private fun onSelectStock(stock: StockPresentation) {
        viewModel.selectStock(stock)
    }

    private fun subscribeUi() {
        observe(viewModel.allStocksLiveData, ::handleStocks)
        observe(viewModel.closeLiveData, ::close)
    }

    private fun handleStocks(allStocksPresentation: AllStocksPresentation) {
        errorLayout.isVisible = allStocksPresentation.showError
        progressBar.isVisible = allStocksPresentation.showProgress
        if (!allStocksPresentation.showProgress) {
            allStocksRecyclerView.isVisible = true
            stocksAdapter.items = allStocksPresentation.stocks
            stocksAdapter.notifyDataSetChanged()
        }
    }

    private fun close(any: Any) {
        findNavController().popBackStack()
    }
}