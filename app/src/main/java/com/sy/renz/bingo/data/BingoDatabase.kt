package com.sy.renz.bingo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PatternData::class, Pattern::class],
    version = 1,
    exportSchema = false
)
abstract class BingoDatabase: RoomDatabase() {
    abstract val dao: PatternDao
    abstract val patternDataDao: PatternDataDao
}