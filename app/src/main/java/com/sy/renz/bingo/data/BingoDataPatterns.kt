package com.sy.renz.bingo.data

import androidx.room.Entity

@Entity(tableName = "BingoDataPatterns", primaryKeys = ["bingoDataId", "patternId"]
)
data class BingoDataPatterns (

//    @PrimaryKey(autoGenerate = true) val bingoDataPatternsId: Long? = null,

    val bingoDataId: Long,

    val patternId: Long,

    val isActive: Boolean = true

)