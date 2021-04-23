package ru.kcoder.stocks.data.persistent

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
abstract class StockDao {

    @Transaction
    open fun selectStock(stockEntity: StockEntity) {
        clear()
        insertStock(stockEntity)
    }

    @Query("DELETE FROM selected_stock")
    abstract fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStock(stockEntity: StockEntity)

    @Query("SELECT * FROM selected_stock LIMIT 1")
    abstract fun observeSelectedStock() : Observable<StockEntity>

    @Query("SELECT * FROM selected_stock LIMIT 1")
    abstract fun getSelectedStock() : Maybe<StockEntity>
}