package com.sy.renz.bingo.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.ui.main_activity.MainActivityEvent
import com.sy.renz.bingo.ui.main_bingo.MainViewModel
import com.sy.renz.bingo.ui.theme.background1
import com.sy.renz.bingo.ui.theme.background2
import com.sy.renz.bingo.ui.theme.darkFont
import com.sy.renz.bingo.ui.theme.fredoka
import com.sy.renz.bingo.util.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: MainViewModel
) {
    val callList = viewModel.callList.split(",").map{ it.toInt() }
    val history = callList.subList(0, viewModel.index + 1)
    println("HISTORY ${viewModel.index}")
    println("HISTORY ${viewModel.callList}")
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
        }
    ) {
        LazyVerticalGrid(
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
                .fillMaxHeight(),
            cells = GridCells.Fixed(5),
            contentPadding = PaddingValues(8.dp),
        ){
            itemsIndexed(history.reversed()) { index, historyItem ->

                //Add 1 to index for obvious reasons.
                HistoryItem(number = historyItem, position = history.size - (index))
            }
        }
    }
}