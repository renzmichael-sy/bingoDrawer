package com.sy.renz.bingo.ui.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sy.renz.bingo.data.BingoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: BingoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel()