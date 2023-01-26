package com.sy.renz.bingo.presentation.ui.settings_screen

sealed class SettingsScreenEvent {

    data class CallTypeEdited(val callType: Int): SettingsScreenEvent()
    data class CallFromEdited(val index: Int): SettingsScreenEvent()
    data class TimerEdited(val timer: Double): SettingsScreenEvent()
    data class AnimationDrawSpeed(val speed: Double): SettingsScreenEvent()
    data class AnnouncementTypeEdited(val type: String): SettingsScreenEvent()
    data class SlowRevealEdited(val toggle: Boolean): SettingsScreenEvent()
    object SaveClicked: SettingsScreenEvent()
}