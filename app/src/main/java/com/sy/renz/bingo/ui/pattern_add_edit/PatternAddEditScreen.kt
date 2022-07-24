package com.sy.renz.bingo.ui.pattern_add_edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternAddEditScreen(
    onPopBackStack:() -> Unit,
    viewModel: PatternAddEditViewModel = hiltViewModel()
) {
//    var patternString: String = "0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0"
//    if(!pattern.isNullOrBlank()) patternString = pattern
//    val pattern = viewModel.patternList.collectAsState(initial = MutableList(25){"0"}.toString())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = background1,
                contentColor = darkFont,
                title = {
                    Text(
                        fontFamily = fredoka,
                        text = "Edit Pattern"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "backIcon")
                    }
                }
            )
        }
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                viewModel.onEvent(PatternAddEditEvent.OnSavePatternClick)
//            }) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Save"
//                )
//            }
//        }
    ){
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            background1,
                            background2
                        ),
                        startY = 150f
                    )
                )
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
//                    .background(color = bg, shape = RoundedCornerShape(percent = 10))
//                    .border(width = 2.dp, color = darkFont, shape = RoundedCornerShape(percent = 10))
            ){


                //Bingo Text
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly){
                    repeat(5) { index ->
                        Text(
                            modifier = Modifier.weight(0.2f),
                            text = stringArrayResource(id = R.array.bingo)[index],
                            color = colorList[index],
                            fontSize = 48.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = fredoka
                        )
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    cells = GridCells.Fixed(5),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalArrangement = Arrangement.SpaceAround
                    ){
                    items(25) { index ->
                        TextButton(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(8.dp, 4.dp, 4.dp, 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if(viewModel.patternString.split(",")[index] == "1") colorList[index % 5] else disabled ),
                            onClick = {

    //                                println("CURRENT PATTERN $pattern " + (index -5))
    //                                pattern.value[index - 5] = "1"
    //                                val newPattern = pattern
    //                                newPattern.value.split(",").toList()[index - 5] = if(newPattern.value[index - 5] == "1") "0" else "1"
                                viewModel.onEvent(PatternAddEditEvent.PatternEdited(index))
    //                                println("CURRENT PATTERN ${pattern.value} " + (pattern.value[index - 5]))
    //                            val edited =
    //                            values.update {  }
    //                            println("CURRENT PATTERN ${values.toString()}")
                            }
                        ) {
                            if(index == 12) {
                                Text(text= "FREE", fontFamily = fredoka, color = color_G, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }

            }

            Text("Quick Controls", color = darkFont)
            //Quick Controls
            Row(modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                OutlinedButton(
                    onClick = { viewModel.onEvent(PatternAddEditEvent.OnToggle(true)) },
                    modifier= Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.White),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                ) {
                    Icon(Icons.Filled.Clear, contentDescription = "Toggle All")
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { viewModel.onEvent(PatternAddEditEvent.OnToggle(false)) },
                    modifier= Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    shape = CircleShape,
                    border= BorderStroke(1.dp, Color.White),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White, backgroundColor = color_G)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Toggle All")
                }
            }

            //Bottom Menu
            Row(modifier = Modifier
                .weight(0.1f)
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
                Column() {
                    Text(text="Let's give this pattern a name", color = Color.White, fontFamily = fredoka, fontSize = 16.sp)
                    OutlinedTextField(
                        value = viewModel.patternName,
                        onValueChange = { viewModel.onEvent(PatternAddEditEvent.OnNameChange(it))},
                        label = { Text("Pattern Name")}
                    )
                }


                TextButton(
                    enabled = (viewModel.patternString.split(",").sumOf { it.toInt() } > 3),
                    onClick = {
                        viewModel.onEvent(PatternAddEditEvent.OnSavePatternClick)
                    }
                ) {

                    Text("Save")
                }
            }
        }

    }

    
}