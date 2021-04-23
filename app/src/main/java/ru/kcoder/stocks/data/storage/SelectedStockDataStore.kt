package ru.kcoder.stocks.data.storage

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.kcoder.stocks.data.persistent.AppDatabase
import ru.kcoder.stocks.data.persistent.StockEntity
import javax.inject.Inject

class SelectedStockDataStore @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun selectStock(stockEntity: StockEntity): Completable {
        return Completable.fromAction {
            appDatabase.selectedStockDao().selectStock(stockEntity)
        }
    }

    fun observeSelectedStock(): Observable<StockEntity> {
        return appDatabase.selectedStockDao().observeSelectedStock()
    }

    fun getSelectedStock(): Maybe<StockEntity> {
        return appDatabase.selectedStockDao().getSelectedStock()
    }
}