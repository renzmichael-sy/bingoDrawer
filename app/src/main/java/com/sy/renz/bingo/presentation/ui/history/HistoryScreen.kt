package com.sy.renz.bingo.presentation.ui.history

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.presentation.ui.theme.background1
import com.sy.renz.bingo.presentation.ui.theme.darkFont
import com.sy.renz.bingo.presentation.ui.theme.fredoka
import com.sy.renz.bingo.util.UiEvent

@Composable
fun HistoryScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val drawList = viewModel.bingoDataAndPattern.value.bingoData.drawList
    val callList: List<Int> = if(drawList != "") drawList.split(",").map{it.toInt()} else emptyList()
    val history = callList.subList(0, viewModel.bingoDataAndPattern.value.bingoData.index + 1)

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
                    IconButton(onClick = { viewModel.onEvent(HistoryScreenEvent.Close) }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
        content = {
            MyLazyColumn(history = history, modifier = Modifier.padding(it))
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