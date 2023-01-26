package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoData
import com.sy.renz.bingo.data.Settings
import javax.inject.Inject

class CreateNewGameUseCase @Inject constructor(
    private val insertBingoDataUseCase: InsertBingoDataUseCase,
    private val generateCallListUseCase: GenerateCallListUseCase,
    private val getDefaultSettingsUseCase: GetDefaultSettingsUseCase,
    private val insertSettingsUseCase: InsertSettingsUseCase
) {
    var settings = Settings()

    suspend operator fun invoke(): BingoData {
        val bingoData = BingoData()

        val defaultSettings = getDefaultSettingsUseCase()

        settings.timer = defaultSettings.timer
        settings.callFrom = defaultSettings.callFrom
        settings.callType = defaultSettings.callType
        settings.animationDrawSpeed = defaultSettings.animationDrawSpeed
        settings.announcerId = defaultSettings.announcerId

        val settingsId = insertSettingsUseCase(settings)

        bingoData.index = -1
        bingoData.settingsId = settingsId
        bingoData.patternId = null
        bingoData.drawList = generateCallListUseCase()

        insertBingoDataUseCase.invoke(bingoData)

        return bingoData

    }

}