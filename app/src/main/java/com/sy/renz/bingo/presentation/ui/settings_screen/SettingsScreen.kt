package com.sy.renz.bingo.presentation.ui.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.UiEvent

@Composable
fun SettingsScreen (
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    onPopBackStack:() -> Unit
){
    val myScaffoldState = rememberScaffoldState()

    val callType = viewModel.callType.value

    val timer = viewModel.timer.value

    val callFrom = viewModel.callFrom.value

    val isSlowReveal = viewModel.isSlowReveal.value


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.Navigate -> TODO()
                is UiEvent.ShowSnackBar -> TODO()
            }
        }
    }

    Scaffold(
        scaffoldState = myScaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar (
                elevation = 0.dp,
                backgroundColor = background1,
                contentColor = darkFont,
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.settings), fontFamily = fredoka)
                },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        }
//        bottomBar = {
//            BottomAppBar(
//                contentPadding = PaddingValues(16.dp)
//            ) {
//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(percent = 50),
//                    colors = ButtonDefaults.buttonColors(backgroundColor = color_I),
//                    onClick = { viewModel.onEvent(MainActivityEvent.SaveClicked) }) {
//                    Text("Save")
//                }
//            }
//        }
    ){
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
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = stringResource(R.string.call_settings), style = Typography.h4)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(color = bg, shape = RoundedCornerShape(percent = 20))
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.call_type))
                Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = { viewModel.onEvent(SettingsScreenEvent.CallTypeEdited(0)) },
                        shape = RoundedCornerShape(
                            topStartPercent = 50,
                            topEndPercent = 0,
                            bottomEndPercent = 0,
                            bottomStartPercent = 25
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (callType == 0) color_G else disabled,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(R.string.continuous))
                    }
                    Button(
                        onClick = { viewModel.onEvent(SettingsScreenEvent.CallTypeEdited(1)) },
                        shape = RoundedCornerShape(
                            topStartPercent = 0,
                            topEndPercent = 25,
                            bottomEndPercent = 50,
                            bottomStartPercent = 0
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (callType == 1) color_G else disabled,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(R.string.intermittent))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
            ) {

                Column(modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .fillMaxHeight()
                    .background(color = bg, shape = RoundedCornerShape(percent = 20))
                    .padding(16.dp)) {
                    Text(stringResource(R.string.call_timer))

                    Row(modifier = Modifier.fillMaxSize()){
                        Button(onClick = {
                            viewModel.onEvent(SettingsScreenEvent.TimerEdited(timer - 1))
                        }){
                            Text(text= stringResource(R.string.minus))
                        }
                        Text(modifier = Modifier.fillMaxHeight(), fontFamily = fredoka, fontSize = 16.sp, text = timer.toString())

                        Button(onClick = {
                            viewModel.onEvent(SettingsScreenEvent.TimerEdited(timer + 1))
                        }){
                            Text(text= stringResource(R.string.plus))
                        }
                    }


                }

                Spacer(modifier = Modifier.fillMaxWidth(0.1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = bg, shape = RoundedCornerShape(percent = 20))
                        .padding(16.dp)
                ) {
                    Text(text = stringResource(R.string.slow_reveal))
                    Switch(checked = isSlowReveal == 1, onCheckedChange = {
                        viewModel.onEvent(SettingsScreenEvent.SlowRevealEdited(it))
                    })
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(color = bg, shape = RoundedCornerShape(percent = 20))
                .padding(16.dp)) {
                Text(stringResource(R.string.call_balls_from))
                Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center) {
                    repeat(5) { index ->
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f),
                            onClick = {
                                viewModel.onEvent(SettingsScreenEvent.CallFromEdited(index))
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White,
                                backgroundColor = if (callFrom.split(",")[index] == "1") colorList[index] else disabled
                            )
                        ) {
                            Text(stringArrayResource(id = R.array.bingo)[index])
                        }
                    }
                }
            }

            Text(stringResource(R.string.announcer_settings))

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
                    )
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

            }
        }

    }
}