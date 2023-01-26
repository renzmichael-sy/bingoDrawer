package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.*
import javax.inject.Inject

class InsertBingoDataUseCase @Inject constructor(
    private val repository: BingoRepository) {

    suspend operator fun invoke(bingoData: BingoData) {
//            settingsId = repository.insertSettings(this@MainViewModel.settings!!)
        repository.insertBingoData(bingoData)

//            if(bingoData.pattern != null) {repository.insertBingoDataPattern(BingoDataPatterns(
//                bingoData.bingoData?.bingoDataId!!, bingoData.pattern.patternId!!, true)) }
//            println("Settings Id: $settingsId bingoDataId: $bingoDataId")

    }

}