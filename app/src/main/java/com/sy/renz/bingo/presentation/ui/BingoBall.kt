package com.sy.renz.bingo.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun BingoBall(letterFontSize: Int= 20,
    numFontSize: Int = 40,
    num: Int = 0,
    slowRevealInt: Int = 0,
    isSlowRevealAffected: Boolean = false){
    val color = when(num) {
        in 1..15 -> color_B
        in 16..30 -> color_I
        in 31..45 -> color_N
        in 46..60 -> color_G
        else -> color_O
    }

    if (num != 0) {
        Column(
            modifier = Modifier
                .background(color = color, shape = CircleShape)
                .aspectRatio(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
            ) {
                AnimatedContent(targetState = num) { editedNum ->
                    AutoResizedText(
                        text = when (editedNum) {
                            in 1..15 -> "B"
                            in 16..30 -> "I"
                            in 31..45 -> "N"
                            in 46..60 -> "G"
                            else -> "O"
                        },
                        style = TextStyle(
                            fontSize = letterFontSize.sp,
                            color = Color.White,
                            fontFamily = fredoka,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                AnimatedContent(targetState = num) { editedNum ->

                    AutoResizedText(
                        text = if(isSlowRevealAffected) if(slowRevealInt > 0) editedNum.toString() else "" else editedNum.toString(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 60.sp,
                            fontFamily = fredoka,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}