package com.sy.renz.bingo.presentation.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sy.renz.bingo.presentation.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.presentation.ui.main_bingo.MainViewModel
import com.sy.renz.bingo.presentation.ui.theme.background1
import com.sy.renz.bingo.presentation.ui.theme.darkFont
import com.sy.renz.bingo.presentation.ui.theme.fredoka
import com.sy.renz.bingo.util.UiEvent

@Composable
fun HistoryScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: MainViewModel
) {
    val callList = viewModel.state.value.callList
    val history = callList.subList(0, viewModel.state.value.index + 1)
//    println("HISTORY ${viewModel.index}")
//    println("HISTORY ${viewModel.callList}")
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackBar -> {

                }
            }
        }
    }

    Scaffold(
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
                    IconButton(onClick = { viewModel.onEvent(MainActivityEvent.HistoryClose) }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
        content = {
            if (history != null) {
                MyLazyColumn(history = history, modifier = Modifier.padding(it))
            }
        }
    ) 
}

@Composable
fun MyLazyColumn( history: List<Int>, modifier: Modifier ) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        modifier = modifier,
        columns = GridCells.Fixed(5)
    ){
        itemsIndexed(history.reversed()) { index, historyItem ->

            //Add 1 to index for obvious reasons.
            HistoryItem(number = historyItem, position = history.size - (index))
        }
    }
}