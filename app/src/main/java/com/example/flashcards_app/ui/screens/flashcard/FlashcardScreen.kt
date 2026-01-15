package com.example.flashcards_app.ui.screens.flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.example.flashcards_app.R
import com.example.flashcards_app.ui.components.SwipeCard
import com.example.flashcards_app.ui.navigation.BottomNavItem.Dashboard.title
import com.example.flashcards_app.ui.theme.darkOrange
import com.example.flashcards_app.ui.theme.darkPurple
import com.example.flashcards_app.ui.theme.gradientColorList
import com.example.flashcards_app.ui.theme.orange
import com.example.flashcards_app.ui.theme.purple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    navController: NavController,
    deckId : Int,
    deckName: String,
    viewModel: FlashcardViewModel = hiltViewModel()
) {
    LaunchedEffect(deckId) {
        viewModel.loadDeck(deckId)
    }

    val cards by viewModel.uiState.collectAsStateWithLifecycle()
    val sortedCards = remember(cards) { cards.sortedByDescending { it.id } }
    val topCard = sortedCards.lastOrNull() // en üsteki karta işlem yapmak için
    var isCurrentFlipped by remember { mutableStateOf(false) }
    var swipeRequest by remember { mutableStateOf<Pair<Int?, Direction>?>(null) } //cardId, direction. 5 idli cardı sağa kaydır

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColorList))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top= 8.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
            ){
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color(0xFFEEEEEE),
                        modifier = Modifier.size(32.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = deckName,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 24.sp,
                color = Color(0xFFEEEEEE)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (cards.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top=30.dp)
                    ) {
                        val composition2 by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.giraffe)
                        )
                        val progress2 by animateLottieCompositionAsState(
                            composition = composition2,
                            iterations = LottieConstants.IterateForever
                        )

                        LottieAnimation(
                            composition = composition2,
                            progress = {progress2},
                            modifier = Modifier.size(250.dp).weight(1f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Congrats! You've studied all the cards in '$deckName' deck." +
                            " You can continue learning by checking out other decks!",
                            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                            textAlign = TextAlign.Center,
                            color = Color(0xFFEEEEEE),
                            fontSize = 20.sp,
                            lineHeight = 45.sp,
                            letterSpacing = 4.sp
                        )

                        val composition1 by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.confettieffect)
                        )
                        val progress1 by animateLottieCompositionAsState(
                            composition = composition1,
                            iterations = 1
                        )

                        LottieAnimation(
                            composition = composition1,
                            progress = {progress1},
                            modifier = Modifier.size(500.dp).weight(1f)
                        )
                    }

                } else {
                    sortedCards.forEachIndexed { index, card ->
                        key(card.id) {
                            val state = rememberSwipeableCardState()
                            val distance = sortedCards.size - 1 - index

                            if (distance < 3) {
                                val showAnswer = (card.id == topCard?.id) && isCurrentFlipped
                                val scale = 1f - (distance * 0.05f)
                                val verticalOffset = (distance * 12).dp

                                if (state.swipedDirection != null) {
                                    LaunchedEffect(card, state.swipedDirection) {
                                        if (state.swipedDirection == Direction.Right) {
                                            viewModel.markAsLearned(card)
                                        } else if (state.swipedDirection == Direction.Left) {
                                            viewModel.markAsReview(card)
                                        }

                                        isCurrentFlipped = false
                                        if (swipeRequest?.first == card.id) swipeRequest = null
                                    }
                                }

                                if (swipeRequest?.first == card.id) {
                                    LaunchedEffect(swipeRequest) {
                                        swipeRequest?.second?.let { state.swipe(it) }
                                    }
                                }

                                Box(
                                    modifier = Modifier.graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                        translationY = verticalOffset.toPx()
                                    }
                                ) {
                                    SwipeCard(
                                        state = state,
                                        questionNo = card.id.toString(),
                                        question = if (showAnswer) card.answer else card.question,
                                        answer = if (showAnswer) card.answer else "...",
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (cards.isNotEmpty()) {
                Row {
                    Button(
                        onClick = {
                            swipeRequest = topCard?.id to Direction.Left
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = darkOrange),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Need to Review",
                            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (isCurrentFlipped) {
                                swipeRequest = topCard?.id to Direction.Right
                            } else {
                                isCurrentFlipped = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCurrentFlipped) Color(0xFF9CAB84) else darkPurple
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if (isCurrentFlipped) "I know it!" else "Reveal the Answer",
                            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                        )
                    }
                }
            }
        }
    }
}

