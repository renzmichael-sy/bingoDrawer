package com.sy.renz.bingo.domain.use_case

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*
import javax.inject.Inject

class TextToSpeechUseCase @Inject constructor(
    private val context: Context
) {
    private var t1: TextToSpeech? = null

    fun initialize() {
        t1 = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                val result = t1!!.setLanguage(Locale.CHINESE)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("Text To Speech", "Language not supported")
                } else {

                }
            } else {
                Log.e("Text to Speech", "Initialization Failed")
            }
        }
    }

    operator fun invoke(text: String){
        if(t1 != null) {
            t1!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        else {
            initialize()
        }

    }



    fun destroy() {
        if (t1 != null) {
            t1!!.stop()
            t1!!.shutdown()
        }
    }
}