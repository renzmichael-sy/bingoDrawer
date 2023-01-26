package com.sy.renz.bingo.presentation.ui.history

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sy.renz.bingo.presentation.ui.BingoBall
import com.sy.renz.bingo.presentation.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HistoryItem(number: Int, position: Int){
    Column(
        Modifier
            .width(IntrinsicSize.Max)
            .padding(4.dp)
    ){
        BingoBall(letterFontSize = 24, numFontSize = 40, num = number)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            fontFamily = fredoka,
            color = darkFont,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = toOrdinal(position))
    }
}

fun toOrdinal(num: Int)= "$num" + if (num % 100 in 11..13) "th" else when (num % 10) {
    1 -> "st"
    2 -> "nd"
    3 -> "rd"
    else -> "th"
}
