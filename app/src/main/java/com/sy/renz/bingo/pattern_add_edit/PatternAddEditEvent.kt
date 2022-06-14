package com.sy.renz.bingo.pattern_add_edit

sealed class PatternAddEditEvent {
    data class PatternEdited(val index: Int): PatternAddEditEvent()
    data class OnNameChange(val name: String): PatternAddEditEvent()
    object OnSavePatternClick: PatternAddEditEvent()

}