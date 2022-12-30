package com.sy.renz.bingo.presentation.ui.main_bingo

import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.data.Settings

data class MainBingoData (
    val bingoData: BingoData,
    val pattern: Pattern,
    val settings: Settings
)