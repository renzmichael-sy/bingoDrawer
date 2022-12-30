package com.sy.renz.bingo.domain.use_case

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.sy.renz.bingo.data.*
import javax.inject.Inject

class InsertBingoDataUseCase @Inject constructor(
    private val repository: BingoRepository) {

    suspend operator fun invoke(bingoData: BingoDataAndPattern?) {
//            settingsId = repository.insertSettings(this@MainViewModel.settings!!)
        if (bingoData != null) {
            repository.insertBingoData(BingoData(bingoDataId = if(bingoData.bingoData?.bingoDataId != 0L) bingoData.bingoData?.bingoDataId else null,
                drawList = bingoData.bingoData?.drawList!!,
                index = bingoData.bingoData.index,
                settingsId = bingoData.bingoData.settingsId))
        }
            if(bingoData?.pattern != null) {repository.insertBingoDataPattern(BingoDataPatterns(
                bingoData.bingoData?.bingoDataId!!, bingoData.pattern.patternId!!, true)) }
//            println("Settings Id: $settingsId bingoDataId: $bingoDataId")

    }

}