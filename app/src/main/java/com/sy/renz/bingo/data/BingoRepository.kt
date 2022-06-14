package com.sy.renz.bingo.data

import kotlinx.coroutines.flow.Flow

interface BingoRepository {
    suspend fun insertPattern(pattern: Pattern)

    suspend fun deletePattern(pattern: Pattern)

    suspend fun getPatternById(id: Int): Pattern?

    fun getPattern(): Flow<List<Pattern>>

    suspend fun insertPatternData(patternData: PatternData)

    suspend fun deletePatternData(patternData: PatternData)

    suspend fun getPatternDataById(id: Int): CompletePatternData?

    fun getCompletePatternData(): Flow<List<CompletePatternData>>
}