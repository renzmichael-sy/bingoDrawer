package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity(tableName = "BingoData")
data class BingoData (
    @PrimaryKey(autoGenerate = true) val bingoDataId: Long? = null,

    @NotNull
    var drawList: String,

    @ColumnInfo(name = "index", defaultValue = "-1")
    var index: Int = -1,

    @Nullable
    @ColumnInfo(name = "customPattern")
    var customPattern: String = "",

    val settingsId: Long


    )
