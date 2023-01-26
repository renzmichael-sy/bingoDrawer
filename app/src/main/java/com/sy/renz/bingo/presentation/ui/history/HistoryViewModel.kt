package com.sy.renz.bingo.presentation.ui.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.BingoDataAndPattern
import com.sy.renz.bingo.domain.use_case.GetLatestBingoDataUseCase
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getLatestBingoDataUseCase: GetLatestBingoDataUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var getBingoDataJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _bingoDataAndPattern = mutableStateOf(BingoDataAndPattern())
    val bingoDataAndPattern: State<BingoDataAndPattern> = _bingoDataAndPattern

    init {
        getBingoData()
    }

    fun onEvent(event: HistoryScreenEvent) {
        when(event) {
            is HistoryScreenEvent.Close -> {
                sendUIEvent(UiEvent.PopBackStack)
            }
        }

    }

    private fun getBingoData() {
        getBingoDataJob?.cancel()
        getBingoDataJob = getLatestBingoDataUseCase().map {
            _bingoDataAndPattern.value = it
        }.launchIn(viewModelScope)
    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}