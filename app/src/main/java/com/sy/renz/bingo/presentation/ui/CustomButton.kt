package com.sy.renz.bingo.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(modifier: Modifier, buttonColors: ButtonColors, buttonText: String, buttonIcon: ImageVector, onClick: () -> Unit, showBottomText: Boolean = true) {
    OutlinedButton(
        modifier= modifier,
        shape = CircleShape,
        border= BorderStroke(1.dp, Color.White),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick , colors = buttonColors) {
        Icon(imageVector = buttonIcon , contentDescription = buttonText, modifier = Modifier.fillMaxSize(0.4f))
    }
}