@file:OptIn(ExperimentalFoundationApi::class)

package com.sy.renz.bingo.presentation.ui.main_bingo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.AdvertView
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.*
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.UiEvent
import java.time.format.TextStyle

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainBingoScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
)
{
    var showDialog by remember { mutableStateOf(false)}
    val scaffoldState = rememberScaffoldState()
    val drawList = viewModel.drawList.value
    val index =  viewModel.index.value
    val pattern = viewModel.pattern.value
    val slowRevealInt = viewModel.slowRevealSteps.value
    val drawType = viewModel.settings.value.callType

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
                BingoColumn(callFrom = viewModel.settings.value.callFrom, historyList = if(drawList.isNotEmpty())drawList.subList(0, index + 1) else emptyList())
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
                                .padding(0.dp,8.dp,8.dp,8.dp)
                                .background(
                                    color = bg,
                                    shape = RoundedCornerShape(
                                        topEndPercent = 20,
                                        topStartPercent = 0,
                                        bottomEndPercent = 20,
                                        bottomStartPercent = 0
                                    ),
                                )
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ){
                            if(index == -1) {
                                AutoResizedText(
                                    modifier = Modifier.fillMaxHeight(0.5f),
                                    text = "Let's Play Bingo!",
                                    style = androidx.compose.ui.text.TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontFamily = fredoka,
                                        fontSize = MaterialTheme.typography.h4.fontSize
                                    ),
                                    color = Color.White,
                                )
                            }
                            else{
                                BingoBall(40, 60, if(drawList.isNotEmpty()) drawList[index] else 0, slowRevealInt = slowRevealInt, isSlowRevealAffected = true)
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    modifier= Modifier
                                        .fillMaxWidth(0.5f)
                                ) {
                                    BingoBall(40, 60, if(drawList.isNotEmpty()) drawList[if(index > 0)index - 1 else 0] else 0)
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = viewModel.timerStateFlow.collectAsState().value.secondsRemaining.toString(),
                                        fontFamily= fredoka,
                                        color = Color.White,
                                        textAlign = TextAlign.Center)
                                }
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
                                .aspectRatio(1f)
                            ) {
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
                                    buttonText = stringResource(R.string.next_ball),
                                    buttonIcon = if(drawType == 0) if(!viewModel.state.value.isDrawing)Icons.Filled.PlayArrow else Icons.Filled.Add else null,
                                    onClick = {viewModel.onEvent(MainActivityEvent.NextBall)},
                                    showBottomText = false,
                                    enabled = index < (drawList.size - 1)
                                )
                            }

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f),
                                buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G),
                                buttonText = "Settings",
                                buttonIcon = Icons.Filled.Settings,
                                onClick = {viewModel.onEvent(MainActivityEvent.SettingsClick)},
                                showBottomText = false
                            )

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f),
                                buttonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G),
                                buttonText = "Call History",
                                buttonIcon = Icons.Filled.List,
                                onClick = {viewModel.onEvent(MainActivityEvent.HistoryClick)},
                                showBottomText = false,
                                enabled = index > 0
                            )

                            CustomButton(
                                modifier = Modifier
                                    .aspectRatio(1f),
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
                        Box(modifier = Modifier.fillMaxHeight(0.7f)) {
                            BingoPattern(pattern)
                        }

                        TextButton(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(color = color_O, shape = RoundedCornerShape(percent = 20)), onClick = {viewModel.onEvent(MainActivityEvent.PatternListClick)}){
                            Text(text = pattern.name, fontFamily = fredoka, color = Color.White)
                        }
                    }
                }
            }
        }

        if(showDialog) {
            CustomAlertDialog(
                title = stringResource(R.string.warning),
                message = stringResource(R.string.restart_confirmation),
                positiveString = stringResource(R.string.yes),
                negativeString = stringResource(R.string.no),
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    viewModel.onEvent(MainActivityEvent.RestartClick)
                }
            )
        }
    }
}