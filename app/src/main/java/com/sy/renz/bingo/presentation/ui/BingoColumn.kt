package com.sy.renz.bingo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.theme.Typography
import com.sy.renz.bingo.presentation.ui.theme.colorList
import com.sy.renz.bingo.presentation.ui.theme.disabled
import com.sy.renz.bingo.presentation.ui.theme.fredoka

@Composable
fun BingoColumn(
    callFrom: String,
    historyList: List<Int>
) {
    var num = 1

    Column(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) {
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(0.dp, 16.dp, 0.dp, 16.dp)
                ) {
                    AutoResizedText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(if (callFrom.split(",")[it] == "1") 1f else 0.5f),
                        text = stringArrayResource(id = R.array.bingo)[it],
                        color = if (callFrom.split(",")[it] == "1") colorList[it] else disabled,
                        style = TextStyle(fontSize = Typography.h1.fontSize, textAlign = TextAlign.Center, fontFamily = fredoka)
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(5) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                        .alpha(if(callFrom.split(",")[it] == "1") 1f else 0.5f)
                ) {
                    repeat(2) { rowIndex ->
                        Column(
                            verticalArrangement = if ((rowIndex % 2) == 0) Arrangement.SpaceAround else Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.1f)

                        ) {
                            repeat(if (rowIndex % 2 == 0) 8 else 7) { columnIndex ->
                                if (rowIndex % 2 == 1 && (columnIndex == 0)) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .aspectRatio(1f)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f)
                                ) {
                                    BingoNumber(num, historyList)
                                }
                                if (rowIndex % 2 == 1 && (columnIndex == 6)) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .aspectRatio(1f)
                                    )
                                }
                                num++
                            }
                        }
                    }
                }
            }
        }
    }
}