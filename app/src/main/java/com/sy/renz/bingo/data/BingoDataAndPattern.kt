package com.sy.renz.bingo.data

import androidx.room.*

data class BingoDataAndPattern (
    @Embedded val bingoData: BingoData,

    @Relation(
        parentColumn = "bingoDataId",
        entityColumn = "patternId",
        associateBy = Junction(BingoDataPatterns::class)
    )
    val patterns: List<Pattern>,


    @Relation(
        parentColumn = "settingsId",
        entityColumn = "id"
    )

    val settings: Settings

)
