package com.sy.renz.bingo.ui.pattern_list

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgument
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.CompletePatternData
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternListViewModel @Inject constructor(
        private val bingoRepository: BingoRepository,
//        private val savedStateHandle: SavedStateHandle
    ): ViewModel(){

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var patternList : Flow<List<CompletePatternData>> = bingoRepository.getCompletePatternData()

    fun onEvent(event: PatternListScreenEvent){
        when(event){
            is PatternListScreenEvent.PatternItemEdit -> {
                println("PATTERN EDIT OPEN")
                sendUIEvent(UiEvent.Navigate(Routes.ADD_EDIT_PATTERN + "?patternId=${event.patternData?.id}"))
            }
            is PatternListScreenEvent.PatternListClosed -> {
                sendUIEvent(UiEvent.PopBackStack)
            }
            is PatternListScreenEvent.PatternItemDelete -> {
                viewModelScope.launch {
                    bingoRepository.deletePatternData(event.patternData)
                }
            }
            else -> {

            }
        }

    }

    private fun sendUIEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}