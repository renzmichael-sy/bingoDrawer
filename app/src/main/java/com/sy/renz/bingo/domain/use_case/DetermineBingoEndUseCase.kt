package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoData
import javax.inject.Inject

class DetermineBingoEndUseCase @Inject constructor() {

    operator fun invoke(bingoData: BingoData): Boolean {
        return bingoData.index < bingoData.drawList.split(",").size

    }
}