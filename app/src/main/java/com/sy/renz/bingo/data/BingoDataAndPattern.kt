package com.sy.renz.bingo.data

import androidx.room.*

data class BingoDataAndPattern (
    @Embedded val bingoData:BingoData = BingoData(),

    @Relation(
        parentColumn = "patternId",
        entityColumn = "patternId"
    )
    val pattern: Pattern? = null,


    @Relation(
        parentColumn = "settingsId",
        entityColumn = "id"
    )

    val settings: Settings? = null

)
