package com.sy.renz.bingo.domain.use_case

import javax.inject.Inject

class GetAnnouncerSpeechUseCase @Inject constructor(
    private val getLatestSettingsUseCase: GetSettingsUseCase
) {
    suspend operator fun invoke(number: Int, isSlowReveal: Boolean = false, slowRevealIndex: Int = 0): String{
        val settings = getLatestSettingsUseCase()
        var letter = "B"
        var num = "1"
        var enunciate = ""


        if(settings.announcementType.contains("1")) {
            when (number) {
                in 16..30 -> {
                    letter = "I"
                }
                in 31..45 -> {
                    letter = "N"
                }
                in 46..60 -> {
                    letter = "G"
                }
                in 61..75 -> {
                    letter = "O"
                }
            }
        }
        if(settings.announcementType.contains("2")) {
            num = number.toString()
        }

        if(settings.announcementType.contains("3")) {
            if(number > 9) {
                enunciate = number.toString().toCharArray().joinToString(",")
            }
            else enunciate = ""
        }

        return if(isSlowReveal) {
            if(slowRevealIndex == 0) letter
            else num + "," + enunciate
        } else {
            letter + "," + num + "," + enunciate
        }
    }
}