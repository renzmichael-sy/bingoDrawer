package com.sy.renz.bingo.presentation.ui.settings_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.Settings
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val bingoRepository: BingoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var settings by mutableStateOf<Settings?>(null)

    var callFrom by mutableStateOf<String>("1,1,1,1,1")

    var timer by mutableStateOf<Double>( 3.0)

    var callType by mutableStateOf<Int> ( 1)

    var announcerId by mutableStateOf<Long?> (null)

    var animationDrawSpeed by mutableStateOf<Double> (2.5)



    init{
        val settingsId =  savedStateHandle.get<Long>("settingsId")!!
        println("Settings ViewModel INIT $settingsId")

        if(settingsId != -1L) {
            viewModelScope.launch{
                bingoRepository.getSettings(settingsId).let {
                    callFrom  = it.callFrom
                    callType = it.callType
                    animationDrawSpeed = it.animationDrawSpeed
                    announcerId = it.announcerId
                    timer = it.timer
                    this@SettingsScreenViewModel.settings = it
                }
            }
        }
    }

    fun onEvent(event: SettingsScreenEvent) {
        when(event) {
            is SettingsScreenEvent.CallTypeEdited -> {
                callType = event.callType
            }
            is SettingsScreenEvent.AnimationDrawSpeed -> TODO()
            is SettingsScreenEvent.CallFromEdited -> {
                val mutableCallFrom = callFrom.split(",").toMutableList()
                mutableCallFrom[event.index] = if(mutableCallFrom[event.index] == "1") "0" else "1"
                callFrom = mutableCallFrom.joinToString(",")
//                insertSettings()
//                generateCallList(true)
            }
            is SettingsScreenEvent.TimerEdited -> {
                timer = event.timer
            }
            is SettingsScreenEvent.SaveClicked -> {
                viewModelScope.launch {
                    bingoRepository.insertSettings(Settings(settings!!.id,callFrom,timer,callType,announcerId,animationDrawSpeed))
                    sendUIEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}