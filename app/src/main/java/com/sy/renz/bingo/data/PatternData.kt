package com.sy.renz.bingo.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PatternData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val isActive: Int = 1
)
