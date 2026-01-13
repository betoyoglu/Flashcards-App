package com.example.flashcards_app.ui.screens.flashcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.example.flashcards_app.R
import com.example.flashcards_app.ui.components.SwipeCard
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    navController: NavController,
    deckId : Int,
    viewModel: FlashcardViewModel = hiltViewModel()
){
    LaunchedEffect(deckId) {
        viewModel.loadDeck(deckId)
    }

    val cards by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope() //butonlar için
    val states = cards.associate { card ->
        card.id to rememberSwipeableCardState() //her card için ayrı yarı state
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if(cards.isEmpty()){
                Text(
                    text = "Congrats! You've studied all the cards in this deck!",
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular))
                )
            }else{
                cards.reversed().forEach { card ->
                    val state = states[card.id]!!

                    if(state.swipedDirection != null){
                        LaunchedEffect(card, state.swipedDirection) {
                            if(state.swipedDirection == Direction.Right){
                                viewModel.markAsLearned(card)
                            }else if(state.swipedDirection == Direction.Left){
                                viewModel.markAsReview(card)
                            }
                        }
                    }

                    SwipeCard(
                        state = state,
                        questionNo = card.id.toString(),
                        question = card.question,
                        answer = card.answer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if(cards.isNotEmpty()){
            Row {
                Button(
                    onClick = {
                        scope.launch {
                            val topCard = cards.last()
                            states[topCard.id]?.swipe(Direction.Left)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(
                        text= "Need to Review",
                        fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                    )
                }

                Button(
                    onClick = {
                        scope.launch {
                            val topCard = cards.last()
                            states[topCard.id]?.swipe(Direction.Right)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text(
                        text= "Reveal the Answer",
                        fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                    )
                }
            }
        }
    }
}
