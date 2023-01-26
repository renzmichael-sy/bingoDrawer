package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pattern")
data class Pattern(
    @PrimaryKey(autoGenerate = true) val patternId: Long? = null,
    val name: String = "",
    val description: String? = null,
    val pattern: String = "",
    @ColumnInfo(name = "isActive", defaultValue = "1")
    val isActive: Int = 1,
    @ColumnInfo(name = "isCustom", defaultValue = "0")
    val isCustom: Int = 0,

    @ColumnInfo(name = "isFavorite", defaultValue = "0")
    val isFavorite: Int = 0

)
