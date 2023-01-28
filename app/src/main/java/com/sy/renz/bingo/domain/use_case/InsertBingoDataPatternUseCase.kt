package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoDataPatterns
import com.sy.renz.bingo.domain.repository.BingoRepository
import javax.inject.Inject

class InsertBingoDataPatternUseCase @Inject constructor(
    private val repository: BingoRepository
) {

    suspend operator fun invoke(id: Long? = null,bingoDataId: Long, patternId: Long): Long {
        return repository.insertBingoDataPattern(BingoDataPatterns(
            bingoDataId = bingoDataId,
            patternId = patternId,
            isActive = true)
        )
    }
}