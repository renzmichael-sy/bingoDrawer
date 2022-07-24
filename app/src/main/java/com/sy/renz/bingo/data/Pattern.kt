package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Pattern")
data class Pattern(
    @PrimaryKey(autoGenerate = true) val patternId: Long?,
    val name: String,
    val description: String?,
    val pattern: String,
    @ColumnInfo(name = "isActive", defaultValue = "1")
    val isActive: Int = 1,
    @NotNull
    @ColumnInfo(name = "isCustom", defaultValue = "0")
    val isCustom: Int = 0,
    @NotNull
    @ColumnInfo(name = "isFavorite", defaultValue = "0")
    val isFavorite: Int = 0

)
