package com.sy.renz.bingo.ui.main_bingo

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.*
import com.sy.renz.bingo.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel@Inject constructor(
        private val repository: BingoRepository
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var settings by mutableStateOf<Settings?>(null)

    private var bingoData by mutableStateOf<BingoData?>(null)

    var bingoPatterns by mutableStateOf<BingoDataAndPattern?>(null)
        private set

    var bingoDataId by mutableStateOf(0L)

    var callList by mutableStateOf("")
        private set
    var index by mutableStateOf(-1)
        private set

    var pattern by mutableStateOf<Pattern?>(null)
        private set

    var customPattern by mutableStateOf<String?>("")
        private set

    //Settings
    var callFrom by mutableStateOf("")
        private set

    var timer by mutableStateOf(0.0)
        private set

    var callType by mutableStateOf(1)
        private set

    var announcerId by mutableStateOf<Long?>(null)
        private set

    var animationDrawSpeed by mutableStateOf(2.5)
        private set

    lateinit var countdown_timer: CountDownTimer

    var progress by mutableStateOf(0.0f)
        private set

    private var timerStarted: Boolean = false


    init{

        viewModelScope.launch() {
//            try {
//                repository.getDefaultSettings()?.let { settings ->
//                    this@MainViewModel.settings = settings
//                }
                repository.getLatestBingoData()?.let { result ->
                    println("DB DATA: $result")
                    callList = result.bingoData.drawList
                    index = result.bingoData.index
                    bingoDataId = result.bingoData.bingoDataId!!
                    pattern = if(result.patterns.isNotEmpty()) result.patterns.last() else null

                    callFrom = result.settings.callFrom
                    callType = result.settings.callType
                    animationDrawSpeed = result.settings.animationDrawSpeed
                    announcerId = result.settings.announcerId
                    this@MainViewModel.bingoPatterns = result
                    this@MainViewModel.settings = result.settings
//                pattern = result.pattern
//                if(result.patternId != null) {
//                    pattern = repository.getPatternById(result.patternId!!)
//                }

                }

            if(bingoDataId == 0L) {
                restartBingo()
            }

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

                toggleTimer()
            }

            is MainActivityEvent.HistoryClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.HISTORY))
            }

            is MainActivityEvent.HistoryClose -> {
                sendUIEvent(UiEvent.PopBackStack)
            }

            is MainActivityEvent.SettingsClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.SETTINGS + "?settingsId=${settings?.id}"))
            }
            is MainActivityEvent.RestartClick -> {
                restartBingo()
            }
            is MainActivityEvent.PatternListClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.PATTERN_LIST))
            }
            is MainActivityEvent.PatternSelected -> {
                pattern = event.pattern
                if(event.customPattern != null) {
                    customPattern = event.customPattern
                }

                insertBingoData()
            }
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun generateCallList(retainCalled: Boolean = false) {
        var newCallList: MutableList<Int> = ArrayList()
        val b = (1..15).toList()
        val i = (16..30).toList()
        val n = (31..45).toList()
        val g = (46..60).toList()
        val o = (61..75).toList()


        val callFrom = callFrom.split(",")

        if (callFrom[0] == "1") newCallList.addAll(b)
        if (callFrom[1] == "1") newCallList.addAll(i)
        if (callFrom[2] == "1") newCallList.addAll(n)
        if (callFrom[3] == "1") newCallList.addAll(g)
        if (callFrom[4] == "1") newCallList.addAll(o)
        newCallList = newCallList.shuffled().toMutableList()


        if (retainCalled && index != -1) {
            newCallList.addAll(0, callList.split(",").subList(0, index).map { it.toInt() })
            newCallList.distinct()
        }

        callList = newCallList.joinToString(",")

        println("CallList $callList")

        insertBingoData()

//        return generateSequence {
//            // this lambda is the source of the sequence's values
//            Random.nextInt(1..75)
//        }
//            .distinct()
//            .take(75)
//            .toList()
//            .joinToString(",")
    }

    private fun insertBingoData(){
        viewModelScope.launch(Dispatchers.IO) {
//            settingsId = repository.insertSettings(this@MainViewModel.settings!!)
            bingoDataId = repository.insertBingoData(BingoData(bingoDataId = if(bingoDataId != 0L) bingoDataId else null, drawList = callList, index = index, settingsId = settings!!.id))
            if(pattern != null) {repository.insertBingoDataPattern(BingoDataPatterns(bingoDataId, pattern!!.patternId!!, true)) }
//            println("Settings Id: $settingsId bingoDataId: $bingoDataId")
        }
    }

//    private fun insertSettings(){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insertSettings(Settings(settingsId,callFrom, timer, callType, announcerId,animationDrawSpeed))
//        }
//    }

    private fun nextBall(){
        if(index < callList.length) {
            index += 1
            insertBingoData()
        }
    }

    private fun toggleTimer(){
        if(timerStarted) {
            countdown_timer.cancel()
            timerStarted = false
        }
        else {
            if(index < callList.length)
                startTimer(5000);
        }
    }

    private fun startTimer(millisInFuture: Long){
        timerStarted = true
        countdown_timer = object:CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progress = millisUntilFinished.toFloat()/millisInFuture.toFloat()
                println("millisUntilFinished $millisUntilFinished PROGRESS $progress")
//                progress = (millisUntilFinished / 1000.00).toFloat()
//                tvMsg.visibility = View.VISIBLE
//                tvMsg.text = msg
            }
            override fun onFinish() {
                nextBall()
                startTimer(5000)
            }
        }.start()
    }

    private fun restartBingo(){
        println("RESTART BINGO")
        index = -1

        pattern = null
//        if(index == 0) {
            viewModelScope.launch {
                repository.getDefaultSettings().let { settings ->
                    println("SETTINGS $settings")
                    callFrom = settings.callFrom
                    callType = settings.callType
                    announcerId = settings.announcerId
                    animationDrawSpeed = settings.animationDrawSpeed
                    this@MainViewModel.settings = settings
                    generateCallList()
                }
            }


//        }
    }
}