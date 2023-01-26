package com.sy.renz.bingo.presentation.ui.pattern_add_edit

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.AutoResizedText
import com.sy.renz.bingo.presentation.ui.theme.*
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
    val bingoPatternWidth = 5
    val bingoPatternHeight = 5

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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
                        text = if(viewModel.patternId.value != null)stringResource(R.string.edit_pattern) else stringResource(R.string.add_new_pattern)
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(
                    colors = listOf(
                        background1,
                        background2
                    ),
                    startY = 150f
                ))
        ){
        Column(
            modifier = Modifier
                .weight(0.8f)
                .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(10))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                repeat(5) {
                    Box(
                        modifier = Modifier.weight(0.2f).aspectRatio(1f)
                    ) {
                        AutoResizedText(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = stringArrayResource(id = R.array.bingo)[it],
                            color = colorList[it],
                            style = TextStyle(
                                fontFamily = fredoka,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().clickable(
                    enabled = false,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {})
                    .padding(it),
            ) {
                repeat(bingoPatternWidth) { columnIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(bingoPatternHeight) { rowIndex ->
                            TextButton(
                                modifier = Modifier
                                    .weight(0.2f)
                                    .aspectRatio(1f)
                                    .padding(8.dp, 4.dp, 4.dp, 8.dp),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (viewModel.patternString.value.split(",")[getIndex(columnIndex, rowIndex, bingoPatternWidth)] == "1"
                                    ) colorList[rowIndex % bingoPatternHeight] else disabled
                                ),
                                onClick = {

                                    //                                println("CURRENT PATTERN $pattern " + (index -5))
                                    //                                pattern.value[index - 5] = "1"
                                    //                                val newPattern = pattern
                                    //                                newPattern.value.split(",").toList()[index - 5] = if(newPattern.value[index - 5] == "1") "0" else "1"
                                    viewModel.onEvent(PatternAddEditEvent.PatternEdited(getIndex(columnIndex, rowIndex, bingoPatternWidth)))
                                    //                                println("CURRENT PATTERN ${pattern.value} " + (pattern.value[index - 5]))
                                    //                            val edited =
                                    //                            values.update {  }
                                    //                            println("CURRENT PATTERN ${values.toString()}")
                                }
                            ) {
                                if (getIndex(columnIndex, rowIndex, bingoPatternWidth) == ((bingoPatternWidth * bingoPatternHeight) / 2)) {
                                    Text(
                                        text = stringResource(R.string.free),
                                        fontFamily = fredoka,
                                        color = color_G,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
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
                Column {
                    Text(text="Let's give this pattern a name", color = Color.White, fontFamily = fredoka, fontSize = 16.sp)
                    OutlinedTextField(
                        value = viewModel.patternName.value,
                        onValueChange = { viewModel.onEvent(PatternAddEditEvent.OnNameChange(it))},
                        label = { Text(stringResource(R.string.pattern_name))}
                    )
                }


                TextButton(
                    enabled = (viewModel.patternString.value.split(",").sumOf { it.toInt() } > 3),
                    onClick = {
                        viewModel.onEvent(PatternAddEditEvent.OnSavePatternClick)
                    }
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}




private fun getIndex(columnIndex: Int, rowIndex: Int, rowTotal: Int): Int {
    return ((columnIndex * rowTotal) + rowIndex)
}

//            Column(
//                modifier = Modifier
//                    .fillMaxHeight(0.8f)
//                    .fillMaxWidth(0.8f)
//                    .padding(16.dp)
////                    .background(color = bg, shape = RoundedCornerShape(percent = 10))
////                    .border(width = 2.dp, color = darkFont, shape = RoundedCornerShape(percent = 10))
//            ){
//                //Bingo Text
//                Row(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly){
//                    repeat(5) { index ->
//
//                    }
//                }
//                LazyVerticalGrid(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    columns = GridCells.Fixed(5),
//                    contentPadding = it,
//                    verticalArrangement = Arrangement.SpaceAround,
//                    horizontalArrangement = Arrangement.SpaceAround
//                    ){
//                    items(25) { index ->
//
//                    }
//                }
//
//            }
//
//
//
//    }
//
//
