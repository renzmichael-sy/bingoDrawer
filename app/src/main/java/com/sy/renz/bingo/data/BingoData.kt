package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BingoData")
data class BingoData (
    @PrimaryKey(autoGenerate = true) val bingoDataId: Long? = null,

    var drawList: String = "",

    @ColumnInfo(name = "index", defaultValue = "-1")
    var index: Int = -1,

    @ColumnInfo(name = "customPattern")
    var customPattern: String = "",

    var settingsId: Long? = null,

    var patternId: Long? = null


)
