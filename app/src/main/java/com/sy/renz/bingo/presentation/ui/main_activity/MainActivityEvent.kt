package com.sy.renz.bingo.presentation.ui.main_activity

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

    data class CallTypeEdited(val callType: Int): MainActivityEvent()
    data class CallFromEdited(val index: Int): MainActivityEvent()
    data class TimerEdited(val timer: Double): MainActivityEvent()
    data class AnimationDrawSpeed(val speed: Double): MainActivityEvent()
    object SaveClicked: MainActivityEvent()


}
