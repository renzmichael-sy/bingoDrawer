package com.sy.renz.bingo.data

import kotlinx.coroutines.flow.Flow

interface BingoRepository {
    suspend fun insertPattern(pattern: Pattern)

    suspend fun deletePattern(pattern: Pattern)

    suspend fun getPatternById(id: Int): Pattern?

    fun getPattern(): Flow<List<Pattern>>

    suspend fun insertBingoData(bingoData: BingoData): Long

    fun getLatestBingoData(): Flow<BingoDataAndPattern>

    suspend fun insertBingoDataPattern(bingoDataPatterns: BingoDataPatterns): Long

    suspend fun deleteBingoDataPattern(bingoDataPatterns: BingoDataPatterns)

    suspend fun getDataWithId(id: Int): BingoDataPatterns?

    suspend fun getPatterns(id: Int): List<BingoDataPatterns>?

    fun getData(id: Long): Flow<BingoDataAndPattern>

    suspend fun getDefaultSettings(): Settings

    suspend fun getSettings(id: Long): Settings

    suspend fun getLatestSettings(): Settings

    suspend fun insertSettings(defaultSettings: Settings): Long
}