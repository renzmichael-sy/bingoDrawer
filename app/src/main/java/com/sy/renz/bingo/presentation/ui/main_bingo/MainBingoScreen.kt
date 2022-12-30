@file:OptIn(ExperimentalFoundationApi::class)

package com.sy.renz.bingo.presentation.ui.main_bingo

import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.ui.*
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.UiEvent


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
//}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainBingoScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel
)
{
    var showDialog by remember { mutableStateOf(false)}
    val scaffoldState = rememberScaffoldState()
//    val callList = if(viewModel.state.value.mainBingoData?.bingoData?.drawList?.isNotBlank() == true) viewModel.state.value.mainBingoData?.bingoData?.drawList?.split(",")
//        ?.map{ it.toInt() } else emptyList()
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
                .padding(it)
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
                val history = viewModel.state.value.callList.subList(0, viewModel.state.value.index + 1)
                BingoColumn(callFrom = viewModel.state.value.settings?.callFrom ?: "1,1,1,1,1", historyList = viewModel.state.value.callList.subList(0, viewModel.state.value.index + 1))
//                BingoColumn(viewModel.state.value.mainBingoData?.bingoData?.drawList?, if(viewModel.index != - 1 && callList.isNotEmpty()) callList.subList(0, viewModel.index + 1) else emptyList())
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
                            BingoBall(20, 40, if(viewModel.state.value.callList.isNotEmpty() && viewModel.state.value.index != -1) viewModel.state.value.callList[viewModel.state.value.index] else 0)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier= Modifier.width(IntrinsicSize.Max)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Previous",
                                    fontFamily= fredoka,
                                    color = Color.White,
                                    textAlign = TextAlign.Center)
                                BingoBall(18, 32, if(viewModel.state.value.callList.isNotEmpty() && viewModel.state.value.index != -1) viewModel.state.value.callList[if(viewModel.state.value.index > 0)viewModel.state.value.index - 1 else 0] else 0)
                            }
                        }

                        //Controls (Next Ball, Settings, History, Reset)
                        Row(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(contentAlignment = Alignment.Center, modifier= Modifier
                                .wrapContentHeight()
                                .aspectRatio(1f)) {
                                CircularProgressIndicator(
                                    color= Color.Yellow,
                                    modifier = Modifier
                                        .fillMaxHeight(0.95f)
                                        .aspectRatio(1f),
//                                    progress = viewModel.progress,
                                    strokeWidth = 5.dp
                                )

                                CustomButton(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .padding(0.dp),
                                    buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_O),
                                    buttonText = "Next Ball",
                                    buttonIcon = Icons.Filled.PlayArrow,
                                    onClick = {viewModel.onEvent(MainActivityEvent.NextBall)},
                                    showBottomText = false
                                )
                            }

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(8.dp),
                                buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G),
                                buttonText = "Settings",
                                buttonIcon = Icons.Filled.Settings,
                                onClick = {viewModel.onEvent(MainActivityEvent.SettingsClick)},
                                showBottomText = false
                            )

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(8.dp),
                                buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G),
                                buttonText = "Call History",
                                buttonIcon = Icons.Filled.List,
                                onClick = {viewModel.onEvent(MainActivityEvent.HistoryClick)},
                                showBottomText = false
                            )

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(8.dp),
                                buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G),
                                buttonText = "Restart",
                                buttonIcon = Icons.Filled.Refresh,
                                onClick = {showDialog = true},
                                showBottomText = false
                            )
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
                            BingoPattern(viewModel.state.value.pattern)
                        }
//                        Spacer(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.05f))
                        TextButton(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
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