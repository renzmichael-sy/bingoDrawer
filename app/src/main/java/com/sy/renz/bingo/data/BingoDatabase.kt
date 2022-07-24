package com.sy.renz.bingo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Pattern::class, BingoData::class, Settings::class,BingoDataPatterns::class],
    version = 1,
    exportSchema = false
)
abstract class BingoDatabase: RoomDatabase() {
    abstract val dao: PatternDao
    abstract val bingoDataDao: BingoDataDao
    abstract val defaultSettingsDao: SettingsDao
    abstract val bingoDataPatternsDao: BingoDataPatternsDao
}