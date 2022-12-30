package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.Settings
import javax.inject.Inject

class GetDefaultSettingsUseCase @Inject constructor(
    private val repository:BingoRepository
){

    suspend operator fun invoke(): Settings {
        repository.getDefaultSettings().let { settings ->
            return settings
//            println("Settings: $settings")
//            timer = settings.timer
//            callFrom = settings.callFrom
//            callType = settings.callType
//            announcerId = settings.announcerId
//            animationDrawSpeed = settings.animationDrawSpeed
//            this@MainViewModel.settings = settings
        }
    }
}