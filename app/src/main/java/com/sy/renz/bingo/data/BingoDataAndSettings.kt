package com.sy.renz.bingo.data

import androidx.room.Embedded
import androidx.room.Relation

data class BingoDataAndSettings (
    @Embedded val bingoData: BingoData,

    @Relation(
        parentColumn = "bingoDataId",
        entityColumn = "id"
    )

    val settings: Settings


)