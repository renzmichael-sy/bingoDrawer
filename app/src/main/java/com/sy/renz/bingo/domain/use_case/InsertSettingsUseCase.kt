package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.Settings
import javax.inject.Inject

class InsertSettingsUseCase @Inject constructor(
    private val repository: BingoRepository
) {

    suspend operator fun invoke(settings: Settings){
        repository.insertSettings(settings)
    }
}