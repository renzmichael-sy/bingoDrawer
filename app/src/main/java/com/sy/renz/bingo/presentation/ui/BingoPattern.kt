package com.sy.renz.bingo.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.R
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent


@ExperimentalFoundationApi
@Composable
fun BingoPattern(pattern: Pattern?) {
    val patternList =
        try {
            pattern?.pattern?.split(",")?.map { it.toInt() } ?: emptyList()
        }
        catch (e: java.lang.NumberFormatException) {
            emptyList()
        }
    Row(
        modifier = Modifier
            .border(1.dp, color = darkFont, shape = RoundedCornerShape(20.dp))
            .background(
                color = bg,
                shape = RoundedCornerShape(20.dp),
            )
            .fillMaxHeight()
            .aspectRatio(0.8f)
            .padding(8.dp, 16.dp, 8.dp, 16.dp)
            .wrapContentSize()
            .clickable {
                UiEvent.Navigate(Routes.PATTERN_LIST)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().clickable(enabled = false, indication = null, interactionSource = remember { MutableInteractionSource() }, onClick = {})
        ) {
            repeat(6) { columnIndex ->
                Row(
                    modifier= Modifier
                        .fillMaxHeight()
                        .weight(0.1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(5) { rowIndex ->
                        if (columnIndex == 0) {
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(0.2f),
                                text = stringArrayResource(id = R.array.bingo)[rowIndex],
                                color = if (patternList.isEmpty()) disabled else colorList[rowIndex],
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = fredoka
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .weight(0.2f)
                                    .padding(1.dp)
                                    .aspectRatio(1f)
                                    .background(
                                        if (patternList.isNotEmpty()) if (patternList[((columnIndex - 1) * 5) + rowIndex] == 1) colorList[rowIndex % 5] else disabled else disabled,
                                        // rounded corner to match with the OutlinedTextField
                                        shape = RoundedCornerShape(percent = 20)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if(patternList.isNotEmpty()) {
                                    if ((((columnIndex - 1) * 5) + rowIndex) == 12 && patternList[((columnIndex - 1) * 5) + rowIndex] == 1) {
                                        Text(
                                            textAlign = TextAlign.Center,
                                            fontFamily = fredoka,
                                            fontSize = 6.sp,
                                            color = color_G,
                                            text = "FREE"
                                        )
                                    }
                                }
                            }
                            //}
                        }
                    }
                }
            }
        }
    }
}
//    LazyVerticalGrid(
//        modifier = Modifier
//            .background(
//                color = bg,
//                shape = RoundedCornerShape(20.dp)
//            ),
//        cells = GridCells.Fixed(5),
//        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
//    ) {
//
//        // +5 to add the header
//        items(30) { item ->
//            if(item < 5) {
//                Text(
//                    modifier = Modifier.padding(0.dp,0.dp,0.dp,8.dp),
//                    text = stringArrayResource(id = R.array.bingo)[item],
//                    color = if(pattern.isEmpty()) disabled else colorList[item % 5],
//                    fontSize = 10.sp,
//                    textAlign = TextAlign.Center,
//                    fontFamily = fredoka
//                )
//            }
//            else {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .aspectRatio(1f)
//                        .padding(2.dp)
//                        .background(
//                            if(pattern[item - 5] == 1) colorList[item % 5] else disabled,
//                            // rounded corner to match with the OutlinedTextField
//                            shape = RoundedCornerShape(4.dp)
//                        )
//                )
//            }
//        }
//    }
//}