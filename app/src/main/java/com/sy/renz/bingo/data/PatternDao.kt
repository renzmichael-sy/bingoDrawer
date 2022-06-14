package com.sy.renz.bingo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PatternDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPattern(pattern: Pattern)

    @Delete
    suspend fun deletePattern(pattern: Pattern)

    @Query("SELECT * FROM pattern where id = :id")
    suspend fun getPatternById(id: Int): Pattern?

    @Query("SELECT * FROM pattern")
    fun getPattern(): Flow<List<Pattern>>
}