package ru.kcoder.stocks.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.kcoder.stocks.data.persistent.StockEntity
import ru.kcoder.stocks.data.storage.AllStocksDataStore
import ru.kcoder.stocks.data.storage.SelectedStockDataStore
import ru.kcoder.stocks.domain.stock.StocksRepository
import ru.kcoder.stocks.domain.stock.Stock
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val allStocksDataStore: AllStocksDataStore,
    private val stockMapper: Stock.Mapper,
    private val stockEntityMapper: StockEntity.Mapper,
    private val selectedStockDataStore: SelectedStockDataStore,
) : StocksRepository {

    override fun getAllStocks(): Single<List<Stock>> {
        return allStocksDataStore.getAllStocks()
            .map(stockMapper::map)
    }


    override fun selectStock(id: String): Completable {
        return allStocksDataStore.getStockById(id)
            .map(stockEntityMapper::map)
            .flatMapCompletable(selectedStockDataStore::selectStock)
    }

    override fun observeSelectedStock(): Observable<Stock> {
        return selectedStockDataStore.observeSelectedStock()
            .map(stockMapper::map)
    }
}