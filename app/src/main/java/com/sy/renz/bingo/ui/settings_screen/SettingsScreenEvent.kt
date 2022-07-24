package com.sy.renz.bingo.ui.settings_screen

import com.sy.renz.bingo.ui.main_activity.MainActivityEvent

sealed class SettingsScreenEvent {

    data class CallTypeEdited(val callType: Int): SettingsScreenEvent()
    data class CallFromEdited(val index: Int): SettingsScreenEvent()
    data class TimerEdited(val timer: Double): SettingsScreenEvent()
    data class AnimationDrawSpeed(val speed: Double): SettingsScreenEvent()
    object SaveClicked: SettingsScreenEvent()
}