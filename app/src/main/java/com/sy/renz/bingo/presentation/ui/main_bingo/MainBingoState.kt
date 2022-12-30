package com.sy.renz.bingo.presentation.ui.main_bingo

import com.sy.renz.bingo.data.BingoDataAndPattern
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.data.Settings
import com.sy.renz.bingo.data.TimerState
import kotlinx.coroutines.flow.StateFlow
import java.util.Timer

data class MainBingoState(
    var isLoading: Boolean = false,
    var index: Int = -1,
//    var mainBingoData: BingoDataAndPattern? = null,
    var callList: List<Int> = emptyList(),
    var error: String = "",
    var settings: Settings? = null,
    var customPattern: String = "",
    var pattern: Pattern? = null,

    val timerState: StateFlow<TimerState>?= null
)

