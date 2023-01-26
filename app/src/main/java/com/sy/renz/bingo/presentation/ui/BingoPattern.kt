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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.Routes
import com.sy.renz.bingo.util.UiEvent
import com.sy.renz.bingo.R


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
            .border(1.dp, color = darkFont, shape = RoundedCornerShape(20))
            .background(
                color = bg,
                shape = RoundedCornerShape(20),
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
                            Box(
                                modifier = Modifier.weight(0.2f)
                            ) {
                                AutoResizedText(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = stringArrayResource(id = R.array.bingo)[rowIndex],
                                    color = if (patternList.isEmpty()) disabled else colorList[rowIndex],
                                    style = TextStyle(fontFamily = fredoka, fontSize = 20.sp, textAlign = TextAlign.Center)
                                )
                            }
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
                                            text = stringResource(R.string.free)
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