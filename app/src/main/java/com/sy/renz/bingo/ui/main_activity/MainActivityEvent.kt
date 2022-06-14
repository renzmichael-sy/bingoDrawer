package com.sy.renz.bingo.ui.main_activity

sealed class MainActivityEvent{
    object NextBall: MainActivityEvent()
    object SettingsClick: MainActivityEvent()
    object HistoryClick: MainActivityEvent()
    object HistoryClose: MainActivityEvent()
    object RestartClick: MainActivityEvent()
    object PatternListClick: MainActivityEvent()


}
