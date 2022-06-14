package com.sy.renz.bingo.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.BingoDatabase
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.BingoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesBingoDatabase(app: Application): BingoDatabase {
        return Room.databaseBuilder(
            app,
            BingoDatabase::class.java,
            "bingo_db"
        ).createFromAsset("database/Bingo.db").build()
    }

    @Provides
    @Singleton
    fun providesBingoRepository(db: BingoDatabase): BingoRepository{
        return BingoRepositoryImpl(db.dao, db.patternDataDao)
    }

    @Provides
    @Singleton
    fun provideBingoData(app:Application): BingoData {
        return BingoData()
    }
}