package ru.kcoder.stocks.presentation.stock

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class StockAdapter(
    onSelectStock: (StockPresentation) -> Unit
) : ListDelegationAdapter<List<StockPresentation>>() {

    init {
        delegatesManager.addDelegate(StockDelegate(onSelectStock))
    }
}