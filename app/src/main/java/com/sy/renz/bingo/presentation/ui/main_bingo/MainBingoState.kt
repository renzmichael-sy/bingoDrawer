package com.sy.renz.bingo.presentation.ui.main_bingo

import com.sy.renz.bingo.data.*
import kotlinx.coroutines.flow.StateFlow

data class MainBingoState (
    var isDrawing: Boolean = false,
    var isLoading: Boolean = false,
//    var index: Int = -1,
//    var bingoDataId: Long? = null,
////    var mainBingoData: BingoDataAndPattern? = null,
//    var callList: List<Int> = emptyList(),
    var error: String = "",

//    var bingoDataPattern: BingoDataAndPattern = BingoDataAndPattern(),

//    var bingoData: BingoData? = null,
//    var settings: Settings? = null,
//    var customPattern: String = "",
//    var pattern: Pattern? = null,

//    val timerState: StateFlow<TimerState>?= null
)

