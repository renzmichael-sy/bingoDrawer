package com.sy.renz.bingo.presentation.ui.pattern_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.data.Pattern
import com.sy.renz.bingo.presentation.ui.BingoPattern
import com.sy.renz.bingo.presentation.ui.theme.*

@Composable
@ExperimentalFoundationApi
fun PatternListItem(index: Int, pattern: Pattern, selected: Boolean, onEvent: (PatternListScreenEvent) -> Unit, modifier : Modifier ){
    Row(
        modifier = Modifier
            .background(bg, shape = RoundedCornerShape(percent = 20))
            .fillMaxWidth()
            .border(2.dp, color = if(selected) color_I else Color.White, shape = RoundedCornerShape(percent = 20))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEvent(PatternListScreenEvent.PatternSelected(pattern)) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier.fillMaxWidth(0.2f).aspectRatio(0.8f)
        ) {
            BingoPattern(pattern = pattern)
        }

        Text(fontFamily = fredoka, fontSize = 24.sp, color = darkFont, modifier = Modifier.padding(16.dp,0.dp,0.dp,0.dp), text = pattern.name)

        Button(
            modifier = Modifier.then(Modifier.size(40.dp)),
            onClick = { onEvent(PatternListScreenEvent.PatternFavorite(pattern, if(pattern.isFavorite == 1) 0 else 1)) },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = color_G,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape
        ) {
            Icon(
                if(pattern.isFavorite == 1) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "favorite",
                tint = Color.White
            )
        }
    }
}