package com.sy.renz.bingo.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "BingoDataPatterns", primaryKeys = ["bingoDataId", "patternId"]
)
data class BingoDataPatterns (

    val bingoDataId: Long,

    val patternId: Long,

    val isActive: Boolean = true

)