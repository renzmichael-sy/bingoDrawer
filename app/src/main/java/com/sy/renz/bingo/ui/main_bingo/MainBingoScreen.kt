@file:OptIn(ExperimentalFoundationApi::class)

package com.sy.renz.bingo.ui.main_bingo

import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringArrayResource
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
import androidx.fragment.app.activityViewModels
import com.sy.renz.bingo.data.Settings
import com.sy.renz.bingo.ui.CustomAlertDialog
import com.sy.renz.bingo.ui.pattern_list.PatternListScreenEvent

@Composable
fun BingoNumbers(
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
            } else disabled)
    }

    Box(contentAlignment= Alignment.Center,
        modifier = Modifier
            .background(color.value, shape = CircleShape)
            .aspectRatio(1f)
            .width(IntrinsicSize.Max)
//            .layout { measurable, constraints ->
//                    // Measure the composable
//                    val placeable = measurable.measure(constraints)
//
//                    //get the current max dimension to assign width=height
//                    val currentHeight = placeable.height
//                    var heightCircle = currentHeight
//                    if (placeable.width > heightCircle)
//                        heightCircle = placeable.width
//
//                    //assign the dimension and the center position
//                    layout(heightCircle, heightCircle) {
//                        // Where the composable gets placed
//                        placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
//                    }
//                }
    ) {

        Text(
            text = num.toString(),
            textAlign = TextAlign.Center,
            color = if(history.contains(num)) Color.White else Color.Gray,
            fontSize = 28.sp,
            fontFamily = fredoka,
            modifier = Modifier
                .defaultMinSize(25.dp) //Use a min size for short text.
        )
    }
}

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
                    color = if(callFrom.split(",")[headerIndex] == "1") colorList[headerIndex] else disabled,
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
                                    Spacer(modifier = Modifier
                                        .weight(0.5f)
                                        .aspectRatio(1f))
                                }
                                Box(modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)) {

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
                                    BingoNumbers(num, historyList)
//                            BingoNumbers(num, historyList)
                                }
                                if (rowIndex % 2 == 1 && (columnIndex == 6)) {
                                    Spacer(modifier = Modifier
                                        .weight(0.5f)
                                        .aspectRatio(1f))
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
//    LazyVerticalGrid(cells = GridCells.Fixed(10)) {
//        items(75) { index ->
//            BingoNumbers((index + 1),historyList)
//        }
//    }
//    var num = 1
//    for (i in 0..4) {
//        Column(
//            modifier = Modifier
//                .padding(0.dp, 8.dp, 0.dp, 0.dp),
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Text(
//                text = stringArrayResource(id = R.array.bingo)[i],
//                color = colorList[i],
//                fontSize = 48.sp,
//                textAlign = TextAlign.Center,
//                fontFamily = fredoka
//            )
//            Row(
//                modifier = Modifier.weight(1f)
//            ) {
//                for (j in 0..1) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight(),
//                        verticalArrangement = Arrangement.SpaceEvenly
//                    ) {
//
//                        var buttonCount = 6
//                        if (j % 2 == 0) {
//                            buttonCount = 7
//                        }
//
//                        for (k in 0..buttonCount) {
//                            BingoNumbers(num,historyList)
//                            num++
//                        }
//                    }
//                }
//
//                if (i < 4)
//                    Spacer(modifier = Modifier.width(8.dp))
//            }
//        }
//    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainBingoScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel
)
{
    var showDialog by remember { mutableStateOf(false)}
    val scaffoldState = rememberScaffoldState()
    val callList = if(viewModel.callList.isNotBlank()) viewModel.callList.split(",").map{ it.toInt() } else emptyList()
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

            //Bingo Numbers List
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                BingoColumn(viewModel.callFrom, if(viewModel.index != - 1 && callList.isNotEmpty()) callList.subList(0, viewModel.index + 1) else emptyList())
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

                        //Call numbers(Current, Previous and label)
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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BingoBall(30, 60, if(callList.isNotEmpty() && viewModel.index != -1) callList[viewModel.index] else 0)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier= Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Previous",
                                    fontFamily= fredoka,
                                    color = Color.White,
                                    textAlign = TextAlign.Center)
                                BingoBall(20, 40, if(callList.isNotEmpty() && viewModel.index != -1) callList[if(viewModel.index > 0)viewModel.index - 1 else 0] else 0)
                            }
                        }

                        //Controls (Next Ball, Settings, History, Reset)
                        Row(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(contentAlignment = Alignment.Center, modifier= Modifier.wrapContentHeight().aspectRatio(1f)) {
                                CircularProgressIndicator(
                                    color= Color.Yellow,
                                    modifier = Modifier.fillMaxHeight(0.95f).aspectRatio(1f),
                                    progress = viewModel.progress,
                                    strokeWidth = 5.dp
                                )
                                OutlinedButton(
                                    onClick = { viewModel.onEvent(MainActivityEvent.NextBall) },
                                    modifier = Modifier
                                        .fillMaxHeight(0.9f)
                                        .aspectRatio(1f),
                                    shape = CircleShape,
                                    border = BorderStroke(1.dp, Color.White),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White,
                                        backgroundColor = color_G
                                    )
                                ) {
                                    Icon(Icons.Filled.CheckCircle, contentDescription = "Draw")
                                }
                            }


                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.SettingsClick)},
                                modifier= Modifier
                                    .fillMaxHeight(0.9f)
                                    .aspectRatio(1f),
                                shape = CircleShape,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ){
                                Icon(Icons.Filled.Settings, contentDescription = "Settings")
                            }

                            OutlinedButton(
                                onClick = { viewModel.onEvent(MainActivityEvent.HistoryClick)},
                                modifier= Modifier
                                    .fillMaxHeight(0.9f)
                                    .aspectRatio(1f),
                                shape = CircleShape,
                                enabled = viewModel.index != -1,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ) {
                                Icon(Icons.Filled.List, contentDescription = "History")
                            }

                            OutlinedButton(
                                onClick = { showDialog = true },
                                modifier= Modifier
                                    .fillMaxHeight(0.9f)
                                    .aspectRatio(1f),
                                shape = CircleShape,
                                border= BorderStroke(1.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                            ) {
                                Icon(Icons.Filled.Refresh, contentDescription = "Reset")
                            }
                        }
                    }

                    //Current Bingo Pattern
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(8.dp)
                            .fillMaxHeight()
                            .clickable {
                                //TODO
                            }
                    ){
//                        Text(
//                            modifier = Modifier.height(IntrinsicSize.Min),
//                            color = Color.White,
//                            fontFamily = fredoka,
//                            fontSize = 16.sp,
//                            text = "${viewModel.pattern?.name}"
//                        )
//                        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                        Box(modifier = Modifier.fillMaxHeight(0.7f)) {
                            BingoPattern(viewModel.pattern)
                        }
//                        Spacer(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.05f))
                        TextButton(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 8.dp)
                            .background(color = color_O, shape = RoundedCornerShape(percent = 20)), onClick = {viewModel.onEvent(MainActivityEvent.PatternListClick)}){
                            Text(text = "Change", fontFamily = fredoka, color = Color.White)
                        }
                    }
                }
            }
        }

        if(showDialog) {
            CustomAlertDialog(
                title = "Warning",
                message = "Are you sure you want to restart?",
                positiveString = "Yes",
                negativeString = "No",
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    viewModel.onEvent(MainActivityEvent.RestartClick)
                }
            )
        }
    }
}