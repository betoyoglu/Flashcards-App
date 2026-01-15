package com.example.flashcards_app.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flashcards_app.R
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.ui.theme.gradientColorList

@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewList by viewModel.reviewCards.collectAsStateWithLifecycle()

    ReviewContent(
        navController = navController,
        reviewList = reviewList
    )
}

@Composable
fun ReviewContent(
    navController: NavController,
    reviewList: List<CardEntity>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .size(300.dp, 200.dp)
                .background(brush = Brush.verticalGradient(gradientColorList))
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color(0xFFEEEEEE),
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = "Need To Review",
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFEEEEEE),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )

            Row (
                modifier = Modifier
                .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = "Decks",
                        fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                        color = Color(0xFFEEEEEE),
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = "Show All",
                        fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                        color = Color(0xFFEEEEEE),
                    )
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){
            LazyColumn {
                items(reviewList) { card ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                    {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        {
                            Text(
                                text = "Question: ${card.question}",
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                            )
                            Text(
                                text = "Answer: ${card.answer}",
                                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ReviewScreenPreview() {
    ReviewContent(
        navController = NavController(LocalContext.current),
        reviewList = listOf(
            CardEntity(1, 1, "What is the capital of France?", "Paris"),
        )
    )
}