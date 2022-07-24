package com.sy.renz.bingo.ui.pattern_add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.Pattern
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

    var patternName by mutableStateOf("")
        private set

    private var patternData by mutableStateOf<Pattern?>(null)

    var patternString by mutableStateOf(List(25){"0"}.joinToString(","))
        private set

    var patternFavorite by mutableStateOf(0)
        private set

    init {
        val patternId =  savedStateHandle.get<Int>("patternId")!!
        println("SAVED STATE HANDLE ID $patternId")
        if(patternId != -1) {
            viewModelScope.launch {
                bingoRepository.getPatternById(patternId)?.let { result ->
                    patternName = result.name
                    patternString = result.pattern
                    patternFavorite = result.isFavorite
                    println("PATTERN STRING: " + patternString)
                    this@PatternAddEditViewModel.patternData = result
                }

            }
        }
    }

    fun onEvent(event: PatternAddEditEvent) {
        when(event) {
            is PatternAddEditEvent.PatternEdited -> {
                val list = patternString.split(",").toMutableList()
                list[event.index] = if(list[event.index] == "1") "0" else "1"
                println("NED STRING ${list.toString()}")
                patternString = list.joinToString(",")
            }

            is PatternAddEditEvent.OnNameChange -> {
                patternName = event.name
            }

            is PatternAddEditEvent.OnFavoriteClick -> {
                patternFavorite = if(patternFavorite == 1) 0 else 1
            }

            is PatternAddEditEvent.OnSavePatternClick -> {
                println("SAVING PATTERN")
                viewModelScope.launch {
                    if(patternName.isBlank()) {
                        sendUIEvent(UiEvent.ShowSnackBar(
                            message = "The name cannot be empty"
                        ))
                        return@launch
                    }
                    bingoRepository.insertPattern(
                        Pattern(
                            name = patternName,
                            description = "",
                            pattern = patternString,
                            isFavorite = patternData?.isFavorite ?: 0,
                            isCustom =  1,
                            patternId = if(patternData != null) patternData?.patternId!! else null
//                            patternId = if(patternData != null) patternData?.patternId else null
                        )
                    )
                    sendUIEvent(UiEvent.PopBackStack)
                }
            }

            is PatternAddEditEvent.OnDeleteClick -> {

            }

            is PatternAddEditEvent.OnToggle -> {
                patternString = List(25) {if(event.toggle) "1" else "0"}.joinToString(",")
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



