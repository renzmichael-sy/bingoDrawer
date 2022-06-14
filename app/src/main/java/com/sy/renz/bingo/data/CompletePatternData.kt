package com.sy.renz.bingo.data

import androidx.room.Embedded
import androidx.room.Relation

data class CompletePatternData (
    @Embedded val patternData: PatternData,
    @Relation(
        parentColumn = "id",
        entityColumn = "dataId"
    )
    val patterns: List<Pattern>
)