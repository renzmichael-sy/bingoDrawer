package com.sy.renz.bingo.presentation.ui.settings_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.Settings
import com.sy.renz.bingo.domain.use_case.*
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val insertSettingsUseCase: InsertSettingsUseCase,
    private val getBingoDataUseCase: GetBingoDataUseCase,
    private val generateCallListUseCase: GenerateCallListUseCase,
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _settings = mutableStateOf<Settings?>(null)
    val settings: State<Settings?> = _settings

    private val _callFrom = mutableStateOf("1,1,1,1,1")
    val callFrom: State<String> = _callFrom

    private val _timer = mutableStateOf( 3.0)
    val timer: State<Double> = _timer

    private val _callType = mutableStateOf ( 1)
    val callType : State<Int> = _callType

    private val _announcerId = mutableStateOf<Long?> (null)
    val announcerId: State<Long?> = _announcerId

    private val _announcementType = mutableStateOf("1,2,3")
    val announcementType: State<String> = _announcementType

    private val _animationDrawSpeed = mutableStateOf (2.5)
    val animationDrawSpeed : State<Double> = _animationDrawSpeed

    private val _isSlowReveal = mutableStateOf(0)
    val isSlowReveal: State<Int> = _isSlowReveal

    private val _bingoData = mutableStateOf(BingoData())


    init{
        val bingoDataId = savedStateHandle.get<Long>("bingoDataId")!!
        if(bingoDataId != -1L) {
            viewModelScope.launch {
                getBingoDataUseCase(bingoDataId).collect {
                    val settings = it.settings
                    _callFrom.value = settings!!.callFrom
                    _callType.value = settings.callType
                    _animationDrawSpeed.value = settings.animationDrawSpeed
                    _announcerId.value = settings.announcerId
                    _timer.value = settings.timer
                    this@SettingsScreenViewModel._settings.value = settings

                    _bingoData.value = it.bingoData
                }
            }
        }
    }

    fun onEvent(event: SettingsScreenEvent) {
        when(event) {
            is SettingsScreenEvent.CallTypeEdited -> {
                _callType.value = event.callType
                insertSettings()
            }
            is SettingsScreenEvent.AnimationDrawSpeed -> TODO()
            is SettingsScreenEvent.CallFromEdited -> {
                val mutableCallFrom = _callFrom.value.split(",").toMutableList()
                mutableCallFrom[event.index] = if(mutableCallFrom[event.index] == "1") "0" else "1"
                _callFrom.value = mutableCallFrom.joinToString(",")
                insertSettings()

                viewModelScope.launch {
                    _bingoData.value = _bingoData.value.copy(drawList = generateCallListUseCase(
                        callList = _bingoData.value.drawList,
                        callFrom = _callFrom.value,
                        retainCalled = true,
                        index = _bingoData.value.index
                    ))
                    insertBingoDataUseCase(_bingoData.value)
                }
            }
            is SettingsScreenEvent.TimerEdited -> {
                _timer.value = event.timer
                insertSettings()
            }
            is SettingsScreenEvent.SaveClicked -> {
                viewModelScope.launch {

                    sendUIEvent(UiEvent.PopBackStack)
                }
            }
            is SettingsScreenEvent.AnnouncementTypeEdited -> {
                _announcementType.value = event.type
                insertSettings()
            }
            is SettingsScreenEvent.SlowRevealEdited -> {
                _isSlowReveal.value = if(event.toggle) 1 else 0
                insertSettings()
            }
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun insertSettings(){
        viewModelScope.launch {
            insertSettingsUseCase(
                Settings(
                    _settings.value?.id,
                    _callFrom.value,
                    _timer.value,
                    _callType.value,
                    _announcerId.value,
                    _announcementType.value,
                    _animationDrawSpeed.value,
                    isSlowReveal = _isSlowReveal.value
                )
            )
        }
    }

}