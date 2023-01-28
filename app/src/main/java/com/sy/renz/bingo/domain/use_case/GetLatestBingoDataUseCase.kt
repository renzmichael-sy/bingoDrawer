package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoDataAndPattern
import com.sy.renz.bingo.domain.repository.BingoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestBingoDataUseCase @Inject constructor(
    private val repository: BingoRepository
) {

    operator fun invoke(): Flow<BingoDataAndPattern> {
        repository.getLatestBingoData().let { result ->
            return result
//            println("DB DATA: $result")
//            callList = result.bingoData.drawList
//            index = result.bingoData.index
//            bingoDataId = result.bingoData.bingoDataId!!
//            pattern = if(result.patterns.isNotEmpty()) result.patterns.last() else null
//
//            callFrom = result.settings.callFrom
//            callType = result.settings.callType
//            animationDrawSpeed = result.settings.animationDrawSpeed
//            announcerId = result.settings.announcerId
//            this@MainViewModel.bingoPatterns = result
//            this@MainViewModel.settings = result.settings
//                pattern = result.pattern
//                if(result.patternId != null) {
//                    pattern = repository.getPatternById(result.patternId!!)
//                }

        }
    }
}