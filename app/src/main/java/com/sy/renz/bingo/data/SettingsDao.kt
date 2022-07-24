package com.sy.renz.bingo.data

import androidx.room.*

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultSettings(defaultSettings: Settings): Long

    @Delete
    suspend fun deleteDefaultSettings(defaultSettings: Settings)

    @Query("SELECT * FROM Settings where id = :id")
    suspend fun getSettingsById(id: Long): Settings

    @Query("SELECT * FROM Settings ORDER BY id DESC LIMIT 1")
    fun getSettingsList(): Settings

    @Query("SELECT * FROM Settings where id = 1")
    suspend fun getDefaultSettings(): Settings
}