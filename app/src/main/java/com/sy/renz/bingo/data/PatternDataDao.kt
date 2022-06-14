package com.sy.renz.bingo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PatternDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatternData(patternData: PatternData)

    @Delete
    suspend fun deletePatternData(patternData: PatternData)

    @Transaction
    @Query("SELECT * FROM PatternData where id = :id")
    suspend fun getPatternDataById(id: Int): CompletePatternData?

    @Transaction
    @Query("SELECT * FROM PatternData")
    fun getCompletePatternData(): Flow<List<CompletePatternData>>
}