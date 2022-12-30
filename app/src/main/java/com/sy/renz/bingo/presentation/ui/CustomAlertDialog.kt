package com.sy.renz.bingo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sy.renz.bingo.presentation.ui.theme.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomAlertDialog (
    title: String,
    message: String,
    positiveString: String,
    negativeString: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    androidx.compose.material.AlertDialog(
        backgroundColor = background1,
        contentColor = darkFont,
//        shape = RoundedCornerShape(percent = 10),
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(16.dp),
        properties = DialogProperties(dismissOnClickOutside = false, usePlatformDefaultWidth = true),

        confirmButton = {
            TextButton(
                modifier = Modifier.background(color = color_G, shape = RoundedCornerShape(percent = 30)),
                onClick = onConfirm
            ) {
                Text(color = androidx.compose.ui.graphics.Color.White, text = positiveString)
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.background(color = color_B, shape = RoundedCornerShape(percent = 30)),
                onClick = onDismiss
            ) {
                Text(color = androidx.compose.ui.graphics.Color.White, text = negativeString)
            }
        },
        title = {
            Text(text = title, fontFamily = fredoka, fontSize = 24.sp, color = colorList[(0..4).random()])
                },
        text = {
            Text(text = message)
        }
    )
}