package com.sy.renz.bingo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.R
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
            repeat(5) { headerIndex ->
                Text(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(0.dp, 16.dp, 0.dp, 16.dp),
                    text = stringArrayResource(id = R.array.bingo)[headerIndex],
                    color = if (callFrom.split(",")[headerIndex] == "1") colorList[headerIndex] else disabled,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fredoka
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(5) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) { rowIndex ->
                        Column(
                            verticalArrangement = if ((rowIndex % 2) == 0) Arrangement.SpaceAround else Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.1f)

//                        .padding(if(rowIndex % 2 == 0) 4.dp else 0.dp,0.dp, if(rowIndex % 2 == 1) 4.dp else 0.dp,0.dp)
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

//                            Text(text = "Top Start", modifier = Modifier
//                                .background(color_G, shape = CircleShape)
//                                .aspectRatio(1f)
//                                .fillMaxWidth(0.25f)
//                                .align(Alignment.TopStart))
//                            Text(text = "Bottom End",, textAlign = Alignment.Center modifier = Modifier
//                                .background(color_G, shape = CircleShape)
//                                .aspectRatio(1f)
//                                .fillMaxWidth(0.25f)
//                                .align(Alignment.BottomEnd))
                                    BingoNumber(num, historyList)
//                            BingoNumbers(num, historyList)
                                }
                                if (rowIndex % 2 == 1 && (columnIndex == 6)) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .aspectRatio(1f)
                                    )
                                }
                                num++

//                    Card(
//                        shape = CircleShape,
//                        backgroundColor = Color.LightGray,
//                        modifier = Modifier
//                            .height(IntrinsicSize.Min)
//                    ) {
//                        Text(
//                            text = num.toString(),
//                            fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .height(IntrinsicSize.Min)
//                                .background(Color.Magenta)
//                        )
//                    }

                            }
                        }

                    }
                }
            }
        }
    }
}