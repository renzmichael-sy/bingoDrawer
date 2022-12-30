package com.sy.renz.bingo.domain.use_case

import javax.inject.Inject

class NextBallUseCase @Inject constructor(
    private val insertBingoDataUseCase: InsertBingoDataUseCase
) {

    operator fun invoke (index: Int): Int {
        var currIndex = index

        return if(currIndex < 75) {
            ++currIndex
        } else
            index


//        if(index < callList.length) {
//            val editedIndex =  index + 1
//            insertBingoDataUseCase()
//        }
    }

}