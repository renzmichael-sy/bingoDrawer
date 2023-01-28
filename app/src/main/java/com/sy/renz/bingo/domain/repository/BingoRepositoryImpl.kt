package com.sy.renz.bingo.domain.repository

import com.sy.renz.bingo.data.*
import kotlinx.coroutines.flow.Flow

class BingoRepositoryImpl(
    private val dao: PatternDao,
    private val bingoDataPatternsDao: BingoDataPatternsDao,
    private val bingoDataDao: BingoDataDao,
    private val settingsDao: SettingsDao
//    private val bingoDataSettingsDao: BingoDataSettingsDao
): BingoRepository {

    override suspend fun insertPattern(pattern: Pattern) {
        dao.insertPattern(pattern)
    }

    override suspend fun deletePattern(pattern: Pattern) {
        dao.deletePattern(pattern)
    }

    override suspend fun getPatternById(id: Int): Pattern? {
        return dao.getPatternById(id)
    }

    override fun getPattern(): Flow<List<Pattern>> {
        return dao.getPattern()
    }

    override suspend fun insertBingoData(bingoData: BingoData): Long {
        return bingoDataDao.insertData(bingoData)
    }

    override fun getLatestBingoData(): Flow<BingoDataAndPattern> {
        return bingoDataDao.getData()
    }

    override suspend fun insertBingoDataPattern(bingoDataPatterns: BingoDataPatterns): Long {
        return bingoDataPatternsDao.insertBingoDataPattern(bingoDataPatterns)
    }

    override suspend fun deleteBingoDataPattern(bingoDataPatterns: BingoDataPatterns) {
        TODO("Not yet implemented")
    }

    override suspend fun getDataWithId(id: Int): BingoDataPatterns? {
        TODO("Not yet implemented")
    }

    override suspend fun getPatterns(id: Int): List<BingoDataPatterns>? {
        TODO("Not yet implemented")
    }

    override fun getData(id: Long): Flow<BingoDataAndPattern> {
        return bingoDataPatternsDao.getData(id)
    }

    override suspend fun getDefaultSettings(): Settings {
        return settingsDao.getDefaultSettings()
    }

    override suspend fun getSettings(id: Long): Settings {
        return settingsDao.getSettingsById(id)
    }

    override suspend fun getLatestSettings(): Settings {
        return settingsDao.getLatestSettings()
    }

    override suspend fun insertSettings(defaultSettings: Settings): Long {
        return settingsDao.insertDefaultSettings(defaultSettings)
    }
}