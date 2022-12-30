package com.sy.renz.bingo.presentation.ui

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.presentation.ui.theme.*

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

//    Box(contentAlignment= Alignment.Center,
//        modifier = Modifier
////            .background(color.value, shape = CircleShape)
//            .aspectRatio(1f)
////            .layout { measurable, constraints ->
////                    // Measure the composable
////                    val placeable = measurable.measure(constraints)
////
////                    //get the current max dimension to assign width=height
////                    val currentHeight = placeable.height
////                    var heightCircle = currentHeight
////                    if (placeable.width > heightCircle)
////                        heightCircle = placeable.width
////
////                    //assign the dimension and the center position
////                    layout(heightCircle, heightCircle) {
////                        // Where the composable gets placed
////                        placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
////                    }
////                }
//    ) {

    Text(
        modifier = Modifier
            .background(color.value, shape = CircleShape)
            .aspectRatio(1f, true),
        text = num.toString(),
        textAlign = TextAlign.Center,
        color = if(history.contains(num)) Color.White else Color.Gray,
        fontSize = 24.sp,
        fontFamily = fredoka,
        maxLines = 1
    )
//    }
}