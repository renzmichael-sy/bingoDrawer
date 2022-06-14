package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "Pattern", foreignKeys = [ForeignKey(
        entity = PatternData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("dataId"),
        onDelete = CASCADE
    )]
)
data class Pattern(
    val pattern: String,
    val isActive: Int = 1,
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "dataId", index = true)
    val dataId: Int
)
