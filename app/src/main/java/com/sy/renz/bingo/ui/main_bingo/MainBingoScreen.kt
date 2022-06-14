package com.sy.renz.bingo.ui.main_bingo

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.ui.BingoBall
import com.sy.renz.bingo.ui.BingoPattern
import com.sy.renz.bingo.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.util.*

@Composable
fun BingoNumbers(
    num: Int,
    history: List<Int>
) {
    val color = remember { Animatable(disabled) }
//    val color =
//        if(history.contains(num)) {
//            when (num) {
//                in 1..15 -> color_B
//                in 16..30 -> color_I
//                in 31..45 -> color_N
//                in 46..60 -> color_G
//                else -> color_O
//            }
//        }
//            else
//                disabled

    LaunchedEffect(history.contains(num)) {
        color.animateTo(if(history.contains(num)) when (num) {
                in 1..15 -> color_B
                in 16..30 -> color_I
                in 31..45 -> color_N
                in 46..60 -> color_G
                else -> color_O
            } else disabled)
    }

    Box(contentAlignment= Alignment.Center,
        modifier = Modifier
            .background(color.value, shape = CircleShape)
            .layout { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                //assign the dimension and the center position
                layout(heightCircle, heightCircle) {
                    // Where the composable gets placed
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            }) {

        Text(
            text = num.toString(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(25.dp) //Use a min size for short text.
        )
    }
}

@Composable
fun BingoHeader(fontSize: Int) {
    val bingo = listOf("B","I","N","G","O")
    for (i in 0..4) {
        Text(
            text = bingo[i],
            color = colorList[i],
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center,
            fontFamily = fredoka
        )
        if (i < 4)
            Spacer(modifier = Modifier.width(16.dp))
    }
}


@Composable
fun BingoColumn(
    historyList: List<Int>
) {
    var num = 1
    for (i in 0..4) {
        Column(
            modifier = Modifier
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringArrayResource(id = R.array.bingo)[i],
                color = colorList[i],
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                fontFamily = fredoka
            )
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for (j in 0..1) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        var buttonCount = 6
                        if (j % 2 == 0) {
                            buttonCount = 7
                        }

                        for (k in 0..buttonCount) {
                            BingoNumbers(num,historyList)
                            num++
                        }
                    }
                }

                if (i < 4)
                    Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainBingoScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
)
{
    val list = viewModel.bingoDataSource.list.collectAsState(initial = emptyList())
    val callInt = viewModel.bingoDataSource.index.collectAsState(initial = 0)
    val scaffoldState = rememberScaffoldState()

    Log.e("START", list.value.toString())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )

                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(MainActivityEvent.RestartClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            background1,
                            background2
                        ),
                        startY = 150f
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                BingoColumn(if(callInt.value != - 1 && list.value.isNotEmpty()) list.value.subList(0, callInt.value + 1) else emptyList())
            }

            //Bottom Controls and Pattern
            Row {
                Row(modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                color_I,
                                color_B
                            )
                        ),
                        RoundedCornerShape(0.dp, 20.dp, 0.dp, 0.dp)
                    )
                )
                {
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f)
                                .padding(0.dp, 8.dp, 8.dp, 8.dp)
                                .background(
                                    color = bg,
                                    shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp),
                                )
                                .padding(8.dp),
                        ) {
                            BingoBall(24, 54, if(list.value.isNotEmpty() && callInt.value != -1) list.value[callInt.value] else 0)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier= Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Previous",
                                    fontFamily= fredoka,
                                    color = Color.White,
                                    textAlign = TextAlign.Center)
                                BingoBall(20, 40, if(list.value.isNotEmpty()) list.value[if(callInt.value > 0)callInt.value - 1 else 0] else 0)
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(8.dp, 8.dp, 8.dp, 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.NextBall)},
                                modifier= Modifier.size(60.dp),
                                shape = CircleShape,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ) {
                                Icon(Icons.Filled.CheckCircle, contentDescription = "Draw")
                            }


                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.PatternListClick)},
                                modifier= Modifier.size(60.dp),
                                shape = CircleShape,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ){
                                Icon(Icons.Filled.Settings, contentDescription = "Settings")
                            }

                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.HistoryClick)},
                                modifier= Modifier.size(60.dp),
                                shape = CircleShape,
                                enabled = callInt.value != -1,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ) {
                                Icon(Icons.Filled.List, contentDescription = "History")
                            }

                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.RestartClick)},
                                modifier= Modifier.size(60.dp),
                                shape = CircleShape,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ) {
                                Icon(Icons.Filled.Refresh, contentDescription = "Reset")
                            }
                        }
                    }

                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp, 8.dp, 8.dp, 8.dp)
                        .weight(0.3f)
                    ){
                        Text(
                            text = "Selected Pattern: M"
                        )
                        BingoPattern("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1")
                    }
                }
            }
        }
    }

}