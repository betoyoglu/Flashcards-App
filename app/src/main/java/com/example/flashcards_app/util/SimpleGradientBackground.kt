package com.example.flashcards_app.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import com.example.flashcards_app.ui.theme.gradientBlue
import com.example.flashcards_app.ui.theme.gradientBluePurple
import com.example.flashcards_app.ui.theme.gradientPurple

fun Modifier.simpleGradient(): Modifier = drawWithCache {
    val gradientBrush = Brush.verticalGradient(listOf(gradientPurple, gradientBluePurple,
        gradientBlue
    ))
    onDrawBehind {
        drawRect(gradientBrush, alpha = 1f)
    }
}