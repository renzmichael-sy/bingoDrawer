package com.sy.renz.bingo.pattern_add_edit

import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.ToggleButton
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sy.renz.bingo.R
import com.sy.renz.bingo.ui.theme.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternAddEditScreen(
    onPopBackStack:() -> Unit,
    viewModel: PatternAddEditViewModel = hiltViewModel()
) {
//    var patternString: String = "0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0"
//    if(!pattern.isNullOrBlank()) patternString = pattern
//    val pattern = viewModel.patternList.collectAsState(initial = MutableList(25){"0"}.toString())

//    LaunchedEffect(key1 = true) {
//        viewModel.patternList.collect {
//            pattern = it
//        }
//    }

    Scaffold(
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
                    IconButton(onClick = { /*viewModel.onEvent(PatternListScreenEvent.PatternListClosed) */}) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "backIcon")
                    }
                }
            )
        }
    ){
        Column(){

//            Text(text = pattern.toString())
//            LazyVerticalGrid(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(background1),
//                cells = GridCells.Fixed(5),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.SpaceAround,
//                horizontalArrangement = Arrangement.SpaceAround
//                ){
//                items(30) { index ->
//
//                    if(index < 5) {
//                        Text(
//                            modifier = Modifier.padding(0.dp,0.dp,0.dp,32.dp),
//                            text = stringArrayResource(id = R.array.bingo)[index],
//                            color = colorList[index],
//                            fontSize = 48.sp,
//                            textAlign = TextAlign.Center,
//                            fontFamily = fredoka
//                        )
//                    }
//
//                    else {
//
//                        TextButton(
//                            modifier = Modifier
//                                .aspectRatio(1f)
//                                .padding(8.dp, 4.dp, 4.dp, 8.dp),
//                            shape = RoundedCornerShape(20.dp),
//                            colors = ButtonDefaults.buttonColors(backgroundColor = if(pattern.value.split(",").toList()[index - 5] == "1") colorList[(index - 5) % 5] else disabled),
//                            onClick = {
//
//                                println("CURRENT PATTERN $pattern " + (index -5))
////                                pattern.value[index - 5] = "1"
////                                val newPattern = pattern
////                                newPattern.value.split(",").toList()[index - 5] = if(newPattern.value[index - 5] == "1") "0" else "1"
//                                viewModel.onEvent(PatternAddEditEvent.PatternEdited(index - 5))
////                                println("CURRENT PATTERN ${pattern.value} " + (pattern.value[index - 5]))
//    //                            val edited =
//    //                            values.update {  }
//    //                            println("CURRENT PATTERN ${values.toString()}")
//                            }
//                        ) {
//                        }
//                    }
//                }
//            }
        }
    }

    
}