package ru.kcoder.stocks.di

import dagger.Binds
import dagger.Module
import ru.kcoder.stocks.config.RxSchedulers
import ru.kcoder.stocks.config.RxSchedulersImpl
import ru.kcoder.stocks.data.repository.StockRateRepositoryImpl
import ru.kcoder.stocks.data.repository.StocksRepositoryImpl
import ru.kcoder.stocks.domain.rate.StockRateRepository
import ru.kcoder.stocks.domain.stock.StocksRepository


@Module
abstract class DataBindings {

    @Binds
    abstract fun provideRxSchedulers(rxSchedulersImpl: RxSchedulersImpl): RxSchedulers

    @Binds
    abstract fun categoryRepository(categoryRepository: StocksRepositoryImpl): StocksRepository

    @Binds
    abstract fun stockRateRepository(stockRateRepository: StockRateRepositoryImpl): StockRateRepository
}