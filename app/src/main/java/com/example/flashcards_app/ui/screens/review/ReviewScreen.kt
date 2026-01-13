package com.example.flashcards_app.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flashcards_app.R

@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewList by viewModel.reviewCards.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        Text(
            text = "Need To Review",
            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            style = MaterialTheme.typography.headlineMedium
        )

        if (reviewList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Great! There are no cards to review. 🎉",
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                )
            }
        } else {
            LazyColumn {
                items(reviewList) { card ->
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth())
                    {
                        Column(modifier = Modifier
                            .padding(16.dp))
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