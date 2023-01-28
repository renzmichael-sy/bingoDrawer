package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.TimerState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CountDownTimerUseCase @Inject constructor(
    private val nextBallUseCase: NextBallUseCase,
    private val timerScope: CoroutineScope
) {
    private val _timerStateFlow = MutableStateFlow(TimerState())
    val timerStateFlow: StateFlow<TimerState> = _timerStateFlow

    private var job: Job? = null

    fun toggleTimer(totalSeconds: Int, bingoData: BingoData) {
        if(job == null) {
            startTimer(totalSeconds, bingoData)
        } else{
            job?.cancel()
            job = null
        }
    }

    private fun startTimer(totalSeconds: Int, bingoData: BingoData){
        job = timerScope.launch {
            initTimer(totalSeconds, bingoData).collect { _timerStateFlow.emit(it) }
        }
    }

    private fun initTimer(totalSeconds: Int, bingoData: BingoData): Flow<TimerState> =
        (totalSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(totalSeconds) }
            .conflate()
            .transform { remainingSeconds: Int ->
                emit(TimerState(remainingSeconds))
            }
            .onCompletion {
                nextBallUseCase(bingoData)
                if(bingoData.index < bingoData.drawList.split(",").size - 1) {
                    startTimer(totalSeconds, bingoData)
                }
                else
                {
                    job?.cancel()
                    job = null
                }
            }
}