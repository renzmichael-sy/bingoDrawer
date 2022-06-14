package com.sy.renz.bingo.ui.pattern_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.data.CompletePatternData
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.ui.BingoPattern
import com.sy.renz.bingo.ui.theme.*

@Composable
@ExperimentalFoundationApi
fun PatternListItem(index: Int, pattern: CompletePatternData, selected: Boolean , onClick: (Int) -> Unit){
    Row(
        modifier = Modifier
            .background(bg, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .border(2.dp, color = if(selected) color_I else Color.White, shape = RoundedCornerShape(20.dp))
            .padding(8.dp)
            .clickable {
                       onClick.invoke(index)
            },

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        BingoPattern(pattern = pattern.patterns[0].pattern)
        pattern.patternData.name?.let { Text(fontFamily = fredoka, fontSize = 24.sp, color = darkFont, modifier = Modifier.padding(16.dp,0.dp,0.dp,0.dp), text = it) }

        Button(
            modifier = Modifier.then(Modifier.size(40.dp)),
            onClick = { TODO() },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = color_G,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape
        ) {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = "favorite",
                tint = Color.White
            )
        }
    }
}