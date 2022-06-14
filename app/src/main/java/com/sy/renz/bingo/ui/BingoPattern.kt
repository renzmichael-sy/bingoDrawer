package com.sy.renz.bingo.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.R
import com.sy.renz.bingo.ui.theme.*


@ExperimentalFoundationApi
@Composable
fun BingoPattern(pattern: String){
    val patternList = pattern.split(",").map{ it.toInt()}
    Row(
        modifier = Modifier
            .border(1.dp, color = darkFont, shape = RoundedCornerShape(20.dp))
            .background(color = bg,
                shape = RoundedCornerShape(20.dp),
            )
            .padding(8.dp,16.dp,8.dp,16.dp)
            .width(IntrinsicSize.Min)
    ){
        for(i in 0..4) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                for(j in 0..5) {
                    if(j == 0) {
                        Text(
                            modifier = Modifier
                                .padding(0.dp,0.dp,0.dp,8.dp)
                                .width(16.dp),
                            text = stringArrayResource(id = R.array.bingo)[i],
                            color = if(patternList.isEmpty()) disabled else colorList[i],
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredoka
                        )
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .height(16.dp)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .background(
                                    if(patternList[(5 * i)+(j-1)] == 1) colorList[i % 5] else disabled,
                                    // rounded corner to match with the OutlinedTextField
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ){
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
}