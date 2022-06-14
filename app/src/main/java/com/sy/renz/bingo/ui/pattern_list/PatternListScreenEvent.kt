package com.sy.renz.bingo.ui.pattern_list

import com.sy.renz.bingo.data.PatternData

sealed class PatternListScreenEvent {
    data class PatternItemEdit(val patternData: PatternData?): PatternListScreenEvent()
    object PatternListClosed: PatternListScreenEvent()
    data class PatternItemDelete(val patternData: PatternData): PatternListScreenEvent()
}