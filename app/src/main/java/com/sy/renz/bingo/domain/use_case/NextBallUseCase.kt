package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoData
import javax.inject.Inject

class NextBallUseCase @Inject constructor(
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase,
    private val getAnnouncerSpeechUseCase: GetAnnouncerSpeechUseCase
) {
    suspend operator fun invoke (bingoData: BingoData, isSlowReveal: Boolean = false) {
        if(bingoData.index < bingoData.drawList.split(",").size) {
            bingoData.index = bingoData.index + 1
            insertBingoDataUseCase(bingoData)
            textToSpeechUseCase.invoke(getAnnouncerSpeechUseCase(bingoData.drawList.split(",")[bingoData.index].toInt()))
        }
    }
}