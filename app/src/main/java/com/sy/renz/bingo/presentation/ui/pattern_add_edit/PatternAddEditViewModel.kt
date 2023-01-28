package com.sy.renz.bingo.presentation.ui.pattern_add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sy.renz.bingo.domain.repository.BingoRepository
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

    private val _patternName = mutableStateOf("")
    val patternName: State<String> = _patternName

    private var _patternData = mutableStateOf<Pattern>(Pattern())
    val patternData: State<Pattern> = _patternData

    private val _patternString = mutableStateOf(List(25){"0"}.joinToString(","))
    val patternString: State<String> = _patternString

    private val _patternFavorite = mutableStateOf(0)
    val patternFavorite: State<Int> = _patternFavorite

    private val _patternId = mutableStateOf<Long?>(null)
    val patternId: State<Long?> = _patternId

    init {
        val patternId =  savedStateHandle.get<Int>("patternId")!!
        println("SAVED STATE HANDLE ID $patternId")
        if(patternId != -1) {
            viewModelScope.launch {
                bingoRepository.getPatternById(patternId)?.let { result ->
                    _patternId.value = result.patternId
                    _patternName.value= result.name
                    _patternString.value = result.pattern
                    _patternFavorite.value = result.isFavorite
                    println("PATTERN STRING: " + patternString)
                    _patternData.value = result
                }

            }
        }
    }

    fun onEvent(event: PatternAddEditEvent) {
        when(event) {
            is PatternAddEditEvent.PatternEdited -> {
                val list = _patternString.value.split(",").toMutableList()

                list[event.index] = if(list[event.index] == "1") "0" else "1"
                println("NED STRING ${list.toString()} " + event.index)
                _patternString.value = list.joinToString(",")
            }

            is PatternAddEditEvent.OnNameChange -> {
                _patternName.value = event.name
            }

            is PatternAddEditEvent.OnFavoriteClick -> {
                _patternFavorite.value = if(_patternFavorite.value == 1) 0 else 1
            }

            is PatternAddEditEvent.OnSavePatternClick -> {
                println("SAVING PATTERN")
                viewModelScope.launch {
                    if(_patternName.value.isBlank()) {
                        sendUIEvent(UiEvent.ShowSnackBar(
                            message = "The name cannot be empty"
                        ))
                        return@launch
                    }
                    bingoRepository.insertPattern(
                        Pattern(
                            name = _patternName.value,
                            description = "",
                            pattern = _patternString.value,
                            isFavorite = _patternData.value.isFavorite ?: 0,
                            isCustom =  1,
                            patternId = patternData.value.patternId
//                            patternId = if(patternData != null) patternData?.patternId else null
                        )
                    )
                    sendUIEvent(UiEvent.PopBackStack)
                }
            }

            is PatternAddEditEvent.OnDeleteClick -> {

            }

            is PatternAddEditEvent.OnToggle -> {
                _patternString.value = List(25) {if(event.toggle) "1" else "0"}.joinToString(",")
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



