package com.example.flashcards_app.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flashcards_app.R
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.ui.theme.gradientColorList
import com.example.flashcards_app.ui.theme.purple

@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewList by viewModel.reviewCards.collectAsStateWithLifecycle()
    val tabs = listOf("Decks", "Show All")

    ReviewContent(
        navController = navController,
        reviewList = reviewList,
        tabs = tabs
    )
}

@Composable
fun ReviewContent(
    navController: NavController,
    reviewList: List<CardEntity>,
    tabs: List<String>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp, 200.dp)
                .background(brush = Brush.verticalGradient(gradientColorList))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back", tint = Color.White)
                }

                Text(
                    text = "Need To Review",
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp).weight(1f)
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Color.White,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(text = title, fontFamily = FontFamily(Font(R.font.robotocondensed_regular))) }
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .background(Color.White)
        ) {
            when (selectedTabIndex) {
                0 -> DecksContent(reviewList = reviewList, onDeckClick = { selectedTabIndex = 1 })
                1 -> ShowAllContent(reviewList)
            }
        }
    }
}

@Composable
fun ShowAllContent(
    reviewList: List<CardEntity>,
){
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

@Composable
fun DecksContent(
    reviewList: List<CardEntity>,
    onDeckClick: () -> Unit
) {
    val groupedDecks = reviewList.groupBy { it.deckId }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(groupedDecks.keys.toList()) { deckName ->
            val cardsInDeck = groupedDecks[deckName] ?: emptyList()

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable{onDeckClick()},
                colors = CardDefaults.cardColors(containerColor = purple)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Deck: $deckName", fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),color = Color.Black)
                        Text(text = "${cardsInDeck.size} cards to review", fontFamily = FontFamily(Font(R.font.robotocondensed_regular)), color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}