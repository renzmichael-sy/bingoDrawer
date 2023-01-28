package com.sy.renz.bingo.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sy.renz.bingo.R
import com.sy.renz.bingo.presentation.ui.theme.bg

@Composable
fun CardBackground() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.2f)
        .background(color = bg, shape = RoundedCornerShape(percent = 20))
        .padding(16.dp)) {
        Text(text = stringResource(R.string.call_type))
    }
}