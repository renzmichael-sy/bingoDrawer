package com.sy.renz.bingo.data

import androidx.room.Entity

@Entity(tableName = "DefaultSettings")
data class DefaultSettings (
    val timer: Int
)
