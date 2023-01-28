package com.sy.renz.bingo.presentation.ui.main_bingo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.*
import com.sy.renz.bingo.domain.use_case.*
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLatestBingoDataUseCase: GetLatestBingoDataUseCase,
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    private val nextBallUseCase: NextBallUseCase,
    private val createNewGameUseCase: CreateNewGameUseCase,
    private var textToSpeechUseCase: TextToSpeechUseCase
): ViewModel() {

    private var getBingoDataJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(MainBingoState())
    val state: State<MainBingoState> = _state

    private var _drawList = mutableStateOf<List<Int>>(emptyList())
    val drawList: State<List<Int>> = _drawList

    private var _bingoDataId = mutableStateOf<Long?>(null)
    val bingoDataId: State<Long?> = _bingoDataId

    private var _index = mutableStateOf(-1)
    val index: State<Int> = _index

    private val _customPattern = mutableStateOf("")

    private val _pattern = mutableStateOf(Pattern())
    val pattern: State<Pattern> = _pattern

    private val _settings = mutableStateOf(Settings())
    val settings: State<Settings> = _settings

    private val _slowRevealSteps = mutableStateOf(2)
    val slowRevealSteps: State<Int> = _slowRevealSteps

    private val _bingoDataPatternId = mutableStateOf<Long?>(null)

    private val timerIntent = CountDownTimerUseCase(timerScope = viewModelScope, nextBallUseCase = nextBallUseCase)

    val timerStateFlow: StateFlow<TimerState> = timerIntent.timerStateFlow

    init{
        textToSpeechUseCase.initialize()
        viewModelScope.launch {
            getBingoData()
        }
    }

    fun onEvent(event: MainActivityEvent) {
        when(event) {
            is MainActivityEvent.NextBall -> {
                viewModelScope.launch {
                    if (_settings.value.callType == 1) {
                        if(_settings.value.isSlowReveal == 1) {
                            when(_slowRevealSteps.value) {
                                0 -> {
                                    _slowRevealSteps.value = 1
                                    textToSpeechUseCase
                                }

                                else -> {
                                    nextBallUseCase(convertBingoData())
                                    _slowRevealSteps.value = 0
                                }
                            }
                        }
                        else {
                            nextBallUseCase(convertBingoData())
                        }
                    } else {
                        println("TOGGLED TIMER ON VM")
                        _state.value = _state.value.copy(isDrawing = !_state.value.isDrawing)
                        timerIntent.toggleTimer(_settings.value.timer.toInt(), convertBingoData())
                    }
                }
            }

            is MainActivityEvent.HistoryClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.HISTORY))
            }

            is MainActivityEvent.SettingsClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.SETTINGS + "?bingoDataId=${_bingoDataId.value}"))
            }
            is MainActivityEvent.RestartClick -> {
                createNewGame()
            }
            is MainActivityEvent.PatternListClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.PATTERN_LIST))
            }
            is MainActivityEvent.PatternSelected -> {
                viewModelScope.launch {
                    _pattern.value = event.pattern
                    insertBingoDataUseCase(convertBingoData())
                }
            }
            else -> {}
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun createNewGame() {
        viewModelScope.launch {
            applyBingoData(BingoDataAndPattern(bingoData = createNewGameUseCase()))
        }
    }

    private fun getBingoData() {
        getBingoDataJob?.cancel()
        getBingoDataJob = getLatestBingoDataUseCase().map {
            applyBingoData(it)
            if(_bingoDataId.value == null) {
                createNewGame()
            }
        }.launchIn(viewModelScope)
    }

    private fun applyBingoData(bingoDataAndPattern: BingoDataAndPattern?){
        if(bingoDataAndPattern != null) {
            _bingoDataId.value = bingoDataAndPattern.bingoData.bingoDataId
            _index.value = bingoDataAndPattern.bingoData.index
            _drawList.value =
                if(bingoDataAndPattern.bingoData.drawList.isNotBlank() && bingoDataAndPattern.bingoData.drawList.isNotEmpty())bingoDataAndPattern.bingoData.drawList.split(",").map { data -> data.toInt() } else emptyList()
            _customPattern.value = bingoDataAndPattern.bingoData.customPattern


            if (bingoDataAndPattern.pattern != null) {
                _pattern.value = bingoDataAndPattern.pattern
            }
            if (bingoDataAndPattern.settings != null) {
                _settings.value = bingoDataAndPattern.settings
            }
        }
    }

    private fun convertBingoData(): BingoData {
        return BingoData(
            bingoDataId = _bingoDataId.value,
            index = _index.value,
            drawList = _drawList.value.joinToString(","),
            settingsId = _settings.value.id,
            patternId = _pattern.value.patternId,
            customPattern = _customPattern.value
        )
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeechUseCase.destroy()
    }
}