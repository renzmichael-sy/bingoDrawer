package com.sy.renz.bingo.domain.use_case

import android.os.CountDownTimer
import com.sy.renz.bingo.data.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountDownTimerUseCase @Inject constructor(
    private val timerScope: CoroutineScope
) {

    private var _timerStateFlow = MutableStateFlow(TimerState())
    val timerStateFlow: StateFlow<TimerState> = _timerStateFlow

    private var job: Job? = null

    fun toggleTimer(totalSeconds: Int) {
        job = if(job == null) {
            timerScope.launch {
                initTimer(totalSeconds)
                    .onCompletion { _timerStateFlow.emit(TimerState()) }
                    .collect{ _timerStateFlow.emit(it)}
            }
        } else{
            job?.cancel()
            null
        }
    }

    private fun initTimer(totalSeconds: Int): Flow<TimerState> =

        (totalSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onStart { emit(totalSeconds) }
            .conflate()
            .transform { remainingSeconds: Int ->
                emit(TimerState(remainingSeconds))
            }
//    suspend operator fun invoke() {
//        val countdown_timer: CountDownTimer
//
//
//    }

}