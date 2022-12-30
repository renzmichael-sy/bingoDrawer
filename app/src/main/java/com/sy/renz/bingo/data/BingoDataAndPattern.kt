package com.sy.renz.bingo.data

import androidx.room.*

data class BingoDataAndPattern (
    @Embedded val bingoData: BingoData? = null,

    @Relation(
        parentColumn = "bingoDataId",
        entityColumn = "patternId",
        associateBy = Junction(BingoDataPatterns::class)
    )
    val pattern: Pattern? = null,


    @Relation(
        parentColumn = "settingsId",
        entityColumn = "id"
    )

    val settings: Settings? = null

)
