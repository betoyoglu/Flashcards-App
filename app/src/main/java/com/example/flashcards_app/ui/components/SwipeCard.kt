package com.example.flashcards_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.SwipeableCardState
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.flashcards_app.R

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun SwipeCard(
    state: SwipeableCardState,
    questionNo: String,
    question: String,
    answer: String,
    modifier: Modifier = Modifier
){
  var isFlipped by remember { mutableStateOf(false) }

    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEEEEE)
        ),
        modifier = modifier
            .size(width = 350.dp, height = 350.dp)
            .swipableCard(
                blockedDirections = listOf(Direction.Down, Direction.Up),
                state = state,
                onSwiped = { direction ->
                    println("The card was swiped to $direction")
                },
                onSwipeCancel = {
                    println("The swiping was cancelled")
                }
            )
    ){
        Column(
           verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = questionNo,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isFlipped) answer else question,
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                fontSize = 20.sp,
            )
        }
    }


}

@Preview
@Composable
fun SwipeCardPreview(){
    SwipeCard(
        questionNo = "1",
        question = "What's the supercontinent broke apart to create the continents we know today?" ,
        answer = "Ankara",
        state = rememberSwipeableCardState(),
        modifier = Modifier
    )
}