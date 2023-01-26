package com.sy.renz.bingo.domain.use_case

import com.sy.renz.bingo.data.BingoRepository
import com.sy.renz.bingo.data.Settings
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: BingoRepository
) {

    suspend operator fun invoke(): Settings{
        repository.getLatestSettings().let {
            return it
        }
    }
}