package com.sy.renz.bingo.presentation.ui.main_bingo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.domain.use_case.*
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel@Inject constructor(
    private val getLatestBingoDataUseCase: GetLatestBingoDataUseCase,
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    private val convertStringToDrawListUseCase: ConvertStringToDrawListUseCase,
    private val nextBallUseCase: NextBallUseCase,
    private val createNewGameUseCase: CreateNewGameUseCase
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

//    private val _index = mutableStateOf<Int>()
//    val index: State<Int> = _index
//
//    private val _callList = mutableStateOf(List<Int>())

    private val _state = mutableStateOf(MainBingoState())
    val state: State<MainBingoState> = _state

    private val timerIntent = CountDownTimerUseCase(viewModelScope)


//    var settings by mutableStateOf<Settings?>(null)
//
//    private var bingoData by mutableStateOf<BingoData?>(null)
//
//    var bingoPatterns by mutableStateOf<BingoDataAndPattern?>(null)
//        private set

//    var bingoDataId by mutableStateOf(0L)
//
//    var callList by mutableStateOf("")
//        private set
//    var index by mutableStateOf(-1)
//        private set
//
//    var pattern by mutableStateOf<Pattern?>(null)
//        private set
//
//    var customPattern by mutableStateOf<String?>("")
//        private set
//
//    //Settings
//    var callFrom by mutableStateOf("")
//        private set
//
//    var timer by mutableStateOf(0.0)
//        private set
//
//    var callType by mutableStateOf(1)
//        private set
//
//    var announcerId by mutableStateOf<Long?>(null)
//        private set
//
//    var animationDrawSpeed by mutableStateOf(2.5)
//        private set
//
//    lateinit var countdown_timer: CountDownTimer
//
//    var progress by mutableStateOf(0.0f)
//        private set
//
//    private var timerStarted: Boolean = false


    init{

        _state.value = _state.value.copy(timerState = timerIntent.timerStateFlow)

        viewModelScope.launch {
            getLatestBingoDataUseCase().let { data ->
//                if (data != null) {
//                    this@MainViewModel._state.value.callList = convertStringToDrawListUseCase(data.bingoData!!.drawList)
//                    this@MainViewModel._state.value.index = data.bingoData.index
//                    this@MainViewModel._state.value.settings = data.settings
//                    this@MainViewModel._state.value.pattern = data.pattern
//                }
//
//                else {
                    createNewGameUseCase().let {
                        this@MainViewModel._state.value = it
                    }
//                }
//                this@MainViewModel._state.value = MainBingoState(mainBingoData = data)
            }

//            convertStringToDrawListUseCase(this@MainViewModel.state.value.mainBingoData.bingoData.drawList)


//            this@MainViewModel.bingoPatterns = getLatestBingoDataUseCase()
//            this@MainViewModel.settings = bingoPatterns!!.settings


//            if(this@MainViewModel._state.value.mainBingoData?.settings == null) {
//                this@MainViewModel._state.value = MainBingoState(mainBingoData = BingoDataAndPattern(settings = getDefaultSettingsUseCase()))
//            }
//            try {
//            repository.getDefaultSettings().let { settings ->
//                println("Settings: $settings")
//                timer = settings.timer
//                callFrom = settings.callFrom
//                callType = settings.callType
//                announcerId = settings.announcerId
//                animationDrawSpeed = settings.animationDrawSpeed
//                this@MainViewModel.settings = settings
//            }



//            if(this@MainViewModel._state.value.mainBingoData?.bingoData?.bingoDataId == 0L) {
//                this@MainViewModel._state.value = createNewGameUseCase()
//            }

//            }catch(e: Exception) {
//
//            }
        }
    }
//        bingoData = BingoData(drawList = callList,index = index, bingoDataId = null)
//        bingoData!!.drawList = callList.joinToString(",")
//    }


    fun onEvent(event: MainActivityEvent) {
        when(event) {
            is MainActivityEvent.NextBall -> {
                if(_state.value.settings?.callType == 0)
                    _state.value = _state.value.copy(
                        index = nextBallUseCase(_state.value.index)
                    )
                else {
                    timerIntent.toggleTimer(5)
                }
            }

            is MainActivityEvent.HistoryClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.HISTORY))
            }

            is MainActivityEvent.HistoryClose -> {
                sendUIEvent(UiEvent.PopBackStack)
            }

            is MainActivityEvent.SettingsClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.SETTINGS + "?settingsId=${_state.value.settings?.id}"))
            }
            is MainActivityEvent.RestartClick -> {
                viewModelScope.launch {
                    createNewGameUseCase().let {
                        this@MainViewModel._state.value
                    }
                }
            }
            is MainActivityEvent.PatternListClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.PATTERN_LIST))
            }
            is MainActivityEvent.PatternSelected -> {
//                this@MainViewModel._state.value = MainBingoState(mainBingoData = BingoDataAndPattern(pattern = event.pattern))
//                pattern = event.pattern
//                if(event.customPattern != null) {
//                    customPattern = event.customPattern
//                }

//                viewModelScope.launch {
//                    insertBingoDataUseCase(_state.value.mainBingoData)
//                }
            }
//            is MainActivityEvent.AnimationDrawSpeed -> {
//                animationDrawSpeed = event.speed
//                insertSettings()
//            }
//            is MainActivityEvent.CallFromEdited -> {
//                val mutableCallFrom = callFrom.split(",").toMutableList()
//                mutableCallFrom[event.index] = if(mutableCallFrom[event.index] == "1") "0" else "1"
//                callFrom = mutableCallFrom.joinToString(",")
//                insertSettings()
//                generateCallListUseCase (true)
//
//            }
//            is MainActivityEvent.CallTypeEdited -> {
//                callType = event.callType
//                insertSettings()
//            }
//            MainActivityEvent.SaveClicked -> TODO()
//            is MainActivityEvent.TimerEdited -> {
//                timer = event.timer
//                insertSettings()
//            }
            else -> {}
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

//    private fun generateCallList(retainCalled: Boolean = false) {


//        return generateSequence {
//            // this lambda is the source of the sequence's values
//            Random.nextInt(1..75)
//        }
//            .distinct()
//            .take(75)
//            .toList()
//            .joinToString(",")
//    }

//    private fun insertBingoData(){
//
//    }

//    private fun insertSettings(){
//        viewModelScope.launch(Dispatchers.IO) {
//
//        }
//    }

//    private fun toggleTimer(){
//        if(timerStarted) {
//            countdown_timer.cancel()
//            timerStarted = false
//        }
//        else {
//            if(index < callList.length) {
//                println("Timer: ${timer.toLong()}")
//                startTimer(timer.toLong() * 1000)
//            }
//        }
//    }
//
//    private fun startTimer(millisInFuture: Long){
//
//        timerStarted = true
//        countdown_timer = object:CountDownTimer(millisInFuture, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                progress = millisUntilFinished.toFloat()/millisInFuture.toFloat()
//                println("millisUntilFinished $millisUntilFinished PROGRESS $progress")
////                progress = (millisUntilFinished / 1000.00).toFloat()
////                tvMsg.visibility = View.VISIBLE
////                tvMsg.text = msg
//            }
//            override fun onFinish() {
//                nextBall()
//                startTimer(millisInFuture)
//            }
//        }.start()
//    }

//    private fun restartBingo(){
//        println("RESTART BINGO")
//        index = -1
//
//        pattern = null
////        if(index == 0) {
//            viewModelScope.launch {
//                repository.getDefaultSettings().let { settings ->
//                    println("SETTINGS $settings")
//                    callFrom = settings.callFrom
//                    callType = settings.callType
//                    announcerId = settings.announcerId
//                    animationDrawSpeed = settings.animationDrawSpeed
//                    this@MainViewModel.settings = settings
//                    generateCallList()
//                }
//            }
//
//
////        }
//    }
}