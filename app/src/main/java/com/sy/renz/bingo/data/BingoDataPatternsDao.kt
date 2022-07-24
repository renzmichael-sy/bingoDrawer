package com.sy.renz.bingo.data

import androidx.room.*

@Dao
interface BingoDataPatternsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBingoDataPattern(bingoDataPatterns: BingoDataPatterns)

    @Delete
    suspend fun deleteBingoDataPattern(bingoDataPatterns: BingoDataPatterns)

    @Transaction
    @Query("SELECT * FROM BingoData")
    fun getPatterns(): List<BingoDataAndPattern>

    @Transaction
    @Query("SELECT * FROM BingoData WHERE bingoDataId = :id")
    fun getData(id: Long): BingoDataAndPattern

}