package com.sy.renz.bingo.data

import kotlinx.coroutines.flow.Flow

class BingoRepositoryImpl(
    private val dao: PatternDao,
    private val patternDataDao: PatternDataDao
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

    override suspend fun insertPatternData(patternData: PatternData) {
        patternDataDao.insertPatternData(patternData)
    }

    override suspend fun deletePatternData(patternData: PatternData) {
        patternDataDao.deletePatternData(patternData)
    }

    override suspend fun getPatternDataById(id: Int): CompletePatternData? {
        return patternDataDao.getPatternDataById(id)
    }

    override fun getCompletePatternData(): Flow<List<CompletePatternData>> {
        return patternDataDao.getCompletePatternData()
    }

}