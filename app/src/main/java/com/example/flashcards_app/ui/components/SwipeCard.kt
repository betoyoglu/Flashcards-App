package com.example.flashcards_app.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.flashcards_app.R

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun SwipeCard(
    questionNo: String,
    question: String,
    answer: String
){
    val state = rememberSwipeableCardState()

    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .swipableCard(
                blockedDirections = listOf(Direction.Down),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 13.dp, bottom = 8.dp)
        ) {
            Text(
                text = questionNo,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = question,
                fontFamily = FontFamily(Font(R.font.googlesans_regular)),
                fontSize = 22.sp,
                maxLines = 2
            )
        }
    }


}

@Preview
@Composable
fun SwipeCardPreview(){
    SwipeCard(
        questionNo = "1",
        question = "What is the capital of Turkey?",
        answer = "Ankara"
    )
}