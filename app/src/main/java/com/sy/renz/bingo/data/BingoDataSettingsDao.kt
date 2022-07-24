package com.sy.renz.bingo.data

import androidx.room.*

@Dao
interface BingoDataSettingsDao {

//    @Insert
//    suspend fun insertSettings(bingoDataSettings: BingoDataSettings)
//
//    @Delete
//    suspend fun deleteBingoDataSettings(bingoDataSettings: BingoDataSettings)

    @Transaction
    @Query("SELECT * FROM BingoData")
    fun getSettings(): List<BingoDataAndPattern>


}