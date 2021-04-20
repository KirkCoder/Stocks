package ru.kcoder.stocks.data.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kcoder.stocks.data.dto.StockDto
import java.lang.IllegalStateException
import javax.inject.Inject

@Entity(tableName = "selected_stock")
data class StockEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
) {
    class Mapper @Inject constructor() {
        fun map(dto: StockDto): StockEntity {
            return if (dto.id != null && dto.name != null) {
                StockEntity(
                    id = dto.id,
                    name = dto.name,
                )
            } else {
                throw IllegalStateException("wrong data in stock dto name: ${dto.name}, id: ${dto.id}")
            }
        }
    }
}