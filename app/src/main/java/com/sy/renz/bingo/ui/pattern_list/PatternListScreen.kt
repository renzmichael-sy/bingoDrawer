package com.sy.renz.bingo.ui.pattern_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.ui.CustomAlertDialog
import com.sy.renz.bingo.ui.theme.*
import com.sy.renz.bingo.util.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternListScreen(
    onPopBackStack:() -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: PatternListViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val patternList = viewModel.patternList.collectAsState(initial = emptyList())
    var selectedIndex by remember{ mutableStateOf(-1)}
    val onItemClick = {
            index: Int -> selectedIndex = index
    }
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
                },
                actions = {
                    Button(
                        modifier = Modifier.then(Modifier.size(40.dp)),
                        onClick = { viewModel.onEvent(PatternListScreenEvent.PatternItemEdit(null)) },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = color_O,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = CircleShape,
                        enabled = selectedIndex != -1
                    ) {
                        Icon(
                            Icons.Filled.Edit,
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
                            backgroundColor = color_B,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = CircleShape,
                        enabled = selectedIndex != -1

                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "delete",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }

            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            background1,
                            background2
                        ),
                        startY = 150f
                    )
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(patternList.value.size) {
                index ->
                    PatternListItem(index, patternList.value[index],selected = selectedIndex == index, onClick = onItemClick)

            }
        }

        if(showDialog) {
            CustomAlertDialog(
                title = "Warning",
                message = "Are you sure you want to delete this pattern?",
                positiveString = "Yes",
                negativeString = "No",
                onDismiss = { showDialog = false },
                onConfirm = { viewModel.onEvent(PatternListScreenEvent.PatternItemDelete(patternList.value[selectedIndex].patternData))}
            )
        }
    }
}