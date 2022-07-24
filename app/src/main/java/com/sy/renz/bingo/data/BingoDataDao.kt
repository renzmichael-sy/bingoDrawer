package com.sy.renz.bingo.data

import androidx.room.*

@Dao
interface BingoDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(bingoData: BingoData): Long

    @Delete
    suspend fun deleteData(bingoData: BingoData)

    @Query("SELECT * FROM BingoData where bingoDataId = :id")
    suspend fun getDataWithId(id: Int): BingoData?

    @Transaction
    @Query("SELECT * FROM BingoData ORDER BY bingoDataId desc LIMIT 1")
    suspend fun getData(): BingoDataAndPattern?

    @Transaction
    @Query("SELECT * FROM BingoData ORDER BY bingoDataId desc LIMIT 1")
    suspend fun getBingoDataAndSettings(): BingoDataAndSettings
}