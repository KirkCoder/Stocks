package ru.kcoder.stocks.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kcoder.stocks.data.persistent.AppDatabase
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun database(context: Context): AppDatabase {
        val roomBuilder = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
        return roomBuilder.build()
    }

    companion object {
        const val APP_DATABASE_NAME = "app_database"
    }

}