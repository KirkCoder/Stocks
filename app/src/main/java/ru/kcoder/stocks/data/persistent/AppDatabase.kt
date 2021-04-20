package ru.kcoder.stocks.data.persistent

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        StockEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun selectedStockDao(): StockDao
}