package com.sy.renz.bingo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sy.renz.bingo.data.BingoDatabase
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.BingoRepositoryImpl
import com.sy.renz.bingo.domain.use_case.TextToSpeechUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        return BingoRepositoryImpl(db.dao, db.bingoDataPatternsDao, db.bingoDataDao, db.defaultSettingsDao)
    }

    @Provides
    @Singleton
    fun providesTextToSpeechUseCase(@ApplicationContext context: Context): TextToSpeechUseCase {
        return TextToSpeechUseCase(context)
    }
}