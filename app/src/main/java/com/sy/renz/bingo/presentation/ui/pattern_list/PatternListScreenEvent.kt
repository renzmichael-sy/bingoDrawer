package com.sy.renz.bingo.presentation.ui.pattern_list

import com.sy.renz.bingo.data.Pattern

sealed class PatternListScreenEvent {
    data class PatternSelected(val pattern:Pattern): PatternListScreenEvent()
    data class PatternItemEdit(val patternId: Long?): PatternListScreenEvent()
    data class PatternFavorite(val pattern: Pattern, val isFavorite: Int) : PatternListScreenEvent()
    object PatternListClosed: PatternListScreenEvent()
    data class PatternItemDelete(val pattern: Pattern): PatternListScreenEvent()
}