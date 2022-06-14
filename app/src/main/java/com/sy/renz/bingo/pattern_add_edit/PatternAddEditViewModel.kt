package com.sy.renz.bingo.pattern_add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.CompletePatternData
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.data.PatternData
import com.sy.renz.bingo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternAddEditViewModel @Inject constructor(
    private val bingoRepository: BingoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var completePatternData by mutableStateOf<CompletePatternData?>(null)
        private set

    var patternData by mutableStateOf<PatternData?>(null)
        private set

    var patternName by mutableStateOf("")
        private set

    var pattern by mutableStateOf<Pattern?>(null)
        private set

    var patternString by mutableStateOf("")
        private set

    init {
        val patternId =  savedStateHandle.get<Int>("patternId")!!
        if(patternId != -1) {
            viewModelScope.launch {
                bingoRepository.getPatternDataById(patternId)?.let { completeData ->
                    patternData = completeData.patternData
                    patternName = completeData.patternData.name.toString()
                    pattern = completeData.patterns.get(0)
                    patternString = completeData.patterns[0].pattern
                    this@PatternAddEditViewModel.completePatternData = completeData
                }

            }
        }
    }

    fun onEvent(event: PatternAddEditEvent) {
        when(event) {
            is PatternAddEditEvent.PatternEdited -> {

            }

            is PatternAddEditEvent.OnNameChange -> {
                patternName = event.name
            }

            is PatternAddEditEvent.OnSavePatternClick -> {
                viewModelScope.launch {
                    if(patternName.isNotBlank()) {
                        sendUIEvent(UiEvent.ShowSnackBar(
                            message = "The name cannot be empty"
                        ))
                        return@launch
                    }
//                    bingoRepository.insertPatternData()
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

//    private val _patternList: MutableStateFlow<String> = MutableStateFlow(List(25){"0"}.toString())
//    val patternList: SharedFlow<String> = _patternList.asSharedFlow()
//
////    init{
////        viewModelScope.launch {
////            _patternList.emit(MutableList(25){"0"})
////        }
//////        _patternList.em
////    }
//
//    fun onEvent(event: PatternAddEditEvent) {
//        when(event) {
//            is PatternAddEditEvent.PatternEdited -> {
//                    viewModelScope.launch{
////                        _patternList.value = MutableList(25) { (0..1).random().toString()}
//                        println("PATTERN CLICKED " + event.index)
////                        var list = MutableList(25) {"0"}
////                        list[event.index] = "1"
//                        _patternList.value = editData(_patternList.value, event.index)
////                        _patternList.update{patternList ->
////                            editData(patternList, event.index)
////                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//fun editData(pattern: String, index: Int): String {
//    val patternList: MutableList<String> = pattern.split(",").toMutableList()
//    patternList[index] = if(patternList[index] == "1") "0" else "1"
////    patternList.get(index) = if(patternList.get(index) = "1" "0" else "1")
//    println("PATTERN $pattern")
//    return patternList.toString()
//}



