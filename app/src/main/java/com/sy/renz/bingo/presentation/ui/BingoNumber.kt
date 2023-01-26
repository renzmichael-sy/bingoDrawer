package com.sy.renz.bingo.presentation.ui

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.theme.*
import androidx.compose.ui.text.TextStyle

@Composable
fun BingoNumber(
    num: Int,
    history: List<Int>
) {
    val color = remember { Animatable(disabled) }

    LaunchedEffect(history.contains(num)) {
        color.animateTo(if(history.contains(num)) when (num) {
            in 1..15 -> color_B
            in 16..30 -> color_I
            in 31..45 -> color_N
            in 46..60 -> color_G
            else -> color_O
        } else disabled
        )
    }

    Box(
        modifier = Modifier.background(color.value, shape = CircleShape).aspectRatio(1f, true),
        contentAlignment = Alignment.Center
    ) {

        AutoResizedText(
            modifier = Modifier,
            text = num.toString(),
            color = if (history.contains(num)) Color.White else Color.Gray,
            style = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Center, fontFamily = fredoka)
        )
    }
//    }
}