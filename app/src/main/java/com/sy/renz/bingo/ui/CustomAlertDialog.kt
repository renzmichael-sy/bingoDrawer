package com.sy.renz.bingo.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.ui.theme.background1
import com.sy.renz.bingo.ui.theme.colorList
import com.sy.renz.bingo.ui.theme.darkFont
import com.sy.renz.bingo.ui.theme.fredoka
import kotlin.random.Random

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
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = positiveString)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = negativeString)
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