package com.sy.renz.bingo.ui.main_bingo

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAdjusters.next
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class MainViewModel@Inject constructor(
        private val bingoData: BingoData,
        private val repository: BingoRepository
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val bingoDataSource = bingoData


    fun onEvent(event: MainActivityEvent) {
        when(event) {
            is MainActivityEvent.NextBall -> {
                bingoDataSource.next()
            }

            is MainActivityEvent.HistoryClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.HISTORY))
            }

            is MainActivityEvent.HistoryClose -> {
                sendUIEvent(UiEvent.PopBackStack)
            }

            is MainActivityEvent.SettingsClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.SETTINGS))
            }
            is MainActivityEvent.RestartClick -> {

            }
            is MainActivityEvent.PatternListClick -> {
                sendUIEvent(UiEvent.Navigate(Routes.PATTERN_LIST))
            }
        }
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}