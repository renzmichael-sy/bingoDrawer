package com.sy.renz.bingo.ui

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.ui.theme.*

fun determineLetter(){

}

@ExperimentalAnimationApi
@Composable
fun BingoBall(letterFontSize: Int, numFontSize: Int, num: Int){
    val letter = when(num) {
        in 1..15 -> "B"
        in 16..30 -> "I"
        in 31..45 -> "N"
        in 46..60 -> "G"
        else -> "O"
    }
    val color = when(num) {
        in 1..15 -> color_B
        in 16..30 -> color_I
        in 31..45 -> color_N
        in 46..60 -> color_G
        else -> color_O
    }


    Column(modifier = Modifier
        .background(color = color, shape = CircleShape)
        .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)){
        AnimatedContent(targetState = num) { editedNum ->

            Text(
                text = when(editedNum) {
                    in 1..15 -> "B"
                    in 16..30 -> "I"
                    in 31..45 -> "N"
                    in 46..60 -> "G"
                    else -> "O"
                },
                fontSize = letterFontSize.sp,
                color = Color.White,
                fontFamily = fredoka,
                textAlign = TextAlign.Center
            )
        }
        AnimatedContent(targetState = num) { editedNum ->


            Text(
                text = editedNum.toString(),
                color = Color.White,
                fontFamily = fredoka,
                textAlign = TextAlign.Center,
                fontSize = numFontSize.sp
            )
        }
    }
}