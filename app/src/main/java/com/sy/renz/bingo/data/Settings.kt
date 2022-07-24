package com.sy.renz.bingo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Settings")
data class Settings (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "callFrom", defaultValue = "1,1,1,1,1")
    var callFrom: String = "1,1,1,1,1",

    @ColumnInfo(name = "timer", defaultValue = "3.0")
    var timer: Double = 3.0,

    @ColumnInfo(name = "callType", defaultValue = "1")
    var callType: Int = 1,

    @ColumnInfo(name = "announcerId")
    var announcerId: Long? = null,

    @ColumnInfo(name = "animationDrawSpeed", defaultValue = "2.5")
    var animationDrawSpeed: Double = 2.5

)
