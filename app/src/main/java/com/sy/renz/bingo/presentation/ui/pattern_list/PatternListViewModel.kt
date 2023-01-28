package com.sy.renz.bingo.presentation.ui.pattern_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.domain.repository.BingoRepository
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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
    var patternList : Flow<List<Pattern>> = bingoRepository.getPattern()

    var selectedPattern by mutableStateOf<Pattern?>(null)
        private set


    fun onEvent(event: PatternListScreenEvent){
        when(event){
            is PatternListScreenEvent.PatternItemEdit -> {
                sendUIEvent(UiEvent.Navigate(Routes.ADD_EDIT_PATTERN + "?patternId=${event.patternId}"))
            }
            is PatternListScreenEvent.PatternListClosed -> {
                sendUIEvent(UiEvent.PopBackStack)
            }
            is PatternListScreenEvent.PatternItemDelete -> {
                viewModelScope.launch {
                    bingoRepository.deletePattern(event.pattern)
                }
            }
            is PatternListScreenEvent.PatternSelected -> {
                selectedPattern = event.pattern
            }
            is PatternListScreenEvent.PatternFavorite -> {
                viewModelScope.launch {
                    bingoRepository.insertPattern(
                        event.pattern.copy(
                            isFavorite = event.isFavorite
                        )
                    )
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