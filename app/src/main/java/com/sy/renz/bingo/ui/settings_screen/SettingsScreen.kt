package com.sy.renz.bingo.ui.settings_screen

import android.content.res.Resources
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.ui.main_bingo.MainViewModel
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SettingsScreen (
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    onPopBackStack:() -> Unit,
){
    val myScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->
            when(event) {
                is UiEvent.PopBackStack -> {
                    println("BOOM")
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
                        text = "Call History", fontFamily = fredoka)
                },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(percent = 50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = color_I),
                    onClick = { viewModel.onEvent(SettingsScreenEvent.SaveClicked) }) {
                    Text("Save")
                }
            }
        }
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
                .padding(16.dp)
        ) {
            Text(text = "Call Settings", style = Typography.h1)

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(color = bg, shape = RoundedCornerShape(percent = 20))
                .padding(16.dp)) {
                Text(text = "Call Type")
                Row(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = { viewModel.onEvent(SettingsScreenEvent.CallTypeEdited(0)) },
                        shape = RoundedCornerShape(topStartPercent = 50, topEndPercent = 0, bottomEndPercent = 0, bottomStartPercent = 25),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if(viewModel.callType == 0) color_G else disabled, contentColor = Color.White)
                    ) {
                        Text(text = "Continuous")
                    }
                    Button(
                        onClick = { viewModel.onEvent(SettingsScreenEvent.CallTypeEdited(1)) },
                        shape = RoundedCornerShape(topStartPercent = 0, topEndPercent = 25, bottomEndPercent = 50, bottomStartPercent = 0),
                        colors = ButtonDefaults.buttonColors(backgroundColor =if (viewModel.callType == 1) color_G else disabled, contentColor = Color.White)
                    ) {
                        Text(text = "Intermittent")
                    }
                }
            }

            Text("Call Timer")

            //TextField(value = 1, onValueChange = )

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(color = bg, shape = RoundedCornerShape(percent = 20))
                .padding(16.dp)) {
                Text("Call Balls From")
                Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    repeat(5) { index ->
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .aspectRatio(1f),
                            onClick = {
                                viewModel.onEvent(SettingsScreenEvent.CallFromEdited(index))
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White,
                                backgroundColor = if (viewModel.callFrom.split(",")[index] == "1") colorList[index] else disabled
                            )
                        ) {
                            Text(stringArrayResource(id = R.array.bingo)[index])
                        }
                    }
                }
            }

            Text("Announcer Settings")


        }

    }
}