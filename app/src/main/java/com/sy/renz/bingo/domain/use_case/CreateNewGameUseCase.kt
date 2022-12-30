package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.BingoDataAndPattern
import com.sy.renz.bingo.presentation.ui.main_bingo.MainBingoState
import javax.inject.Inject

class CreateNewGameUseCase @Inject constructor(
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    private val generateCallListUseCase: GenerateCallListUseCase,
    private val getDefaultSettingsUseCase: GetDefaultSettingsUseCase,
    private val convertStringToDrawListUseCase: ConvertStringToDrawListUseCase
) {
    suspend operator fun invoke(): MainBingoState {
        val mainBingoState = MainBingoState()
        mainBingoState.settings = getDefaultSettingsUseCase()
        mainBingoState.callList = convertStringToDrawListUseCase(generateCallListUseCase())

        insertBingoDataUseCase.invoke(BingoDataAndPattern(BingoData(null, mainBingoState.callList.toString(), mainBingoState.index, mainBingoState.customPattern, mainBingoState.settings!!.id)))

        return mainBingoState
    }

}