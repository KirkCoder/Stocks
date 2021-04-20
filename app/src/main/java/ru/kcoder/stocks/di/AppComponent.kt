package ru.kcoder.stocks.di

import dagger.Component
import ru.kcoder.stocks.App
import ru.kcoder.stocks.presentation.base.BaseFragment
import ru.kcoder.stocks.presentation.rates.RatesFragment
import ru.kcoder.stocks.presentation.stock.StockFragment
import javax.inject.Singleton


@Component(
    modules = [
        AndroidModule::class,
        DataBindings::class,
        GsonModule::class,
        ApiModule::class,
        PersistenceModule::class,
    ]
)
@Singleton
interface AppComponent {
    fun inject(app: App)

    fun inject(baseFragment: BaseFragment)

    fun inject(ratesFragment: RatesFragment)

    fun inject(stockFragment: StockFragment)
}