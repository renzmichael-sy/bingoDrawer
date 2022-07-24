package com.sy.renz.bingo.ui.pattern_add_edit

sealed class PatternAddEditEvent {
    data class PatternEdited(val index: Int): PatternAddEditEvent()
    data class OnNameChange(val name: String): PatternAddEditEvent()
    data class OnToggle(val toggle: Boolean): PatternAddEditEvent()
    object OnFavoriteClick: PatternAddEditEvent()
    object OnSavePatternClick: PatternAddEditEvent()
    object OnDeleteClick: PatternAddEditEvent()

}