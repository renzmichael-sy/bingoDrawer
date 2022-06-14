package com.sy.renz.bingo.data

import kotlinx.coroutines.flow.*
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

class BingoData {
    var drawList: MutableStateFlow<List<Int>> = MutableStateFlow(generateSequence {
        // this lambda is the source of the sequence's values
        Random.nextInt(1..75)
    }
        .distinct()
        .take(75)
        .toList())
    val list = drawList.asSharedFlow()

    private val _index: MutableStateFlow<Int> = MutableStateFlow(-1)
    val index = _index.asSharedFlow()

    fun next() {
        _index.update { index -> index + 1 }
    }
}
