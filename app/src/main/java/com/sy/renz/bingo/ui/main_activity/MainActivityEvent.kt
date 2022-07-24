package com.sy.renz.bingo.ui.main_activity

import com.sy.renz.bingo.data.Pattern

sealed class MainActivityEvent{
    object NextBall: MainActivityEvent()
    object SettingsClick: MainActivityEvent()
    object HistoryClick: MainActivityEvent()
    object HistoryClose: MainActivityEvent()
    object RestartClick: MainActivityEvent()
    object PatternListClick: MainActivityEvent()
    data class PatternSelected(val pattern: Pattern, val customPattern: String?): MainActivityEvent()
//    data class Announcer


}
