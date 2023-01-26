package com.sy.renz.bingo.presentation.ui.pattern_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.presentation.ui.CustomAlertDialog
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.presentation.ui.main_bingo.MainViewModel
import com.sy.renz.bingo.presentation.ui.theme.*
import com.sy.renz.bingo.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternListScreen(
    onPopBackStack:() -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    mainViewModel: MainViewModel,
    viewModel: PatternListViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val patternList = viewModel.patternList.collectAsState(initial = emptyList())
//    var selectedIndex by remember{ mutableStateOf(-1)}
//    val onItemClick = {
//            index: Int -> selectedIndex = index
//    }
    var showDialog by remember { mutableStateOf(false)}

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
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else ->Unit
            }

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = background1,
                contentColor = darkFont,
                title = {
                    Text(
                        fontFamily = fredoka,
                        text = "Pattern List"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(PatternListScreenEvent.PatternListClosed )}) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "backIcon")
                    }
                }
            )
        }


    ) {
        Column(
            modifier = Modifier.background(Brush.verticalGradient(
                colors = listOf(
                    background1,
                    background2
                ),
                startY = 150f
            ))
            .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.9f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(patternList.value.size + 1) { index ->
                    if (index == 0) {
                        Box(
                            modifier = Modifier.background(color = color_B, shape = RoundedCornerShape(percent = 10)).fillMaxWidth().aspectRatio(4f).clickable { viewModel.onEvent(PatternListScreenEvent.PatternItemEdit(null)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                fontFamily = fredoka,
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                                text = "Create a New Pattern"
                            )
                        }

                    }  else {
                        PatternListItem(
                            index = index,
                            pattern = patternList.value[index - 1],
                            selected = patternList.value[index - 1] == viewModel.selectedPattern,
                            viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(bg, shape = RoundedCornerShape(20.dp))
                                .border(
                                    2.dp,
                                    color = if (viewModel.selectedPattern == patternList.value[index - 1]) color_I else Color.White,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(8.dp)
                        )
                    }

                }
            }

            //Bottom menu
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
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
                    .padding(16.dp)
            ){
                TextButton(
                    modifier = Modifier.weight(1f).background(color = color_G, shape = RoundedCornerShape(percent = 40)),
                    onClick = {
                            mainViewModel.onEvent(MainActivityEvent.PatternSelected(viewModel.selectedPattern!!,customPattern = null))
                            onPopBackStack()
                        },
                    enabled = viewModel.selectedPattern != null
                ){
                    Text(text = "Use this Pattern", fontSize = 24.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.then(Modifier.size(40.dp)),
                    onClick = { viewModel.onEvent(PatternListScreenEvent.PatternItemEdit(
                        viewModel.selectedPattern?.patternId
                    )) },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = color_O,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    enabled = viewModel.selectedPattern != null
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.then(Modifier.size(40.dp)),
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = color_O,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    enabled = viewModel.selectedPattern != null

                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = Color.White
                    )
                }
            }
        }



        if(showDialog) {
            CustomAlertDialog(
                title = "Warning",
                message = "Are you sure you want to delete this pattern?",
                positiveString = "Yes",
                negativeString = "No",
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    viewModel.onEvent(PatternListScreenEvent.PatternItemDelete(viewModel.selectedPattern!!))
                }
            )
        }
    }
}