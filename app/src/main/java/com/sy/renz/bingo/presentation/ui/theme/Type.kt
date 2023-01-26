package com.sy.renz.bingo.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sy.renz.bingo.R

// Set of Material typography styles to start with
val abeezee = FontFamily(
    Font(R.font.abeezee_regular, FontWeight.Normal)
)
val fredoka = FontFamily(
    Font(R.font.fredoka_one, FontWeight.Normal)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = fredoka,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    defaultFontFamily = abeezee,
    h1 = TextStyle(color = Color.White, fontSize = 48.sp, fontFamily = fredoka)
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
