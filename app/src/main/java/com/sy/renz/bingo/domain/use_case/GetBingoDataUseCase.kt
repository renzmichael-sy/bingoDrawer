package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoDataAndPattern
import com.sy.renz.bingo.domain.repository.BingoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBingoDataUseCase @Inject constructor(
    private val repository: BingoRepository
) {
    operator fun invoke(bingoDataId: Long): Flow<BingoDataAndPattern> {
        return repository.getData(bingoDataId)
    }
}