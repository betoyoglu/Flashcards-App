package com.example.flashcards_app.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.flashcards_app.R
import com.example.flashcards_app.data.local.entity.DeckEntity
import com.example.flashcards_app.data.model.DeckSummary
import com.example.flashcards_app.ui.components.DeckCard
import com.example.flashcards_app.ui.components.StatusCards
import com.example.flashcards_app.ui.theme.darkOrange
import com.example.flashcards_app.ui.theme.darkPurple
import com.example.flashcards_app.ui.theme.orange
import com.example.flashcards_app.ui.theme.purple

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()){
    val deckList by viewModel.decks.collectAsStateWithLifecycle()
    val studiedCards by viewModel.studiedCards.collectAsStateWithLifecycle()
    val reviewCards by viewModel.reviewCards.collectAsStateWithLifecycle()
    val cardList by viewModel.cards.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()

    DashboardContent(
        navController = navController,
        studiedCardCount = studiedCards.size,
        deckCount = deckList.size,
        deckList = deckList,
        reviewCount = reviewCards.size,
        username = username
    )

}

@Composable
fun DashboardContent(
    navController: NavController,
    studiedCardCount: Int,
    deckCount: Int,
    reviewCount: Int,
    deckList: List<DeckSummary> = emptyList(),
    username : String
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome back $username !",
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular))
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.giraffe_icon),
                contentDescription = "icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatusCards(
                count = studiedCardCount,
                description = "Studied Cards",
                containerColor = purple,
                contentColor = darkPurple,
            )
            StatusCards(
                count = deckCount,
                description = "Decks Created",
                containerColor = orange,
                contentColor = darkOrange,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Decks",
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            if(reviewCount > 0){
                item {
                    DeckCard(
                        deckName = "Need To Review Again",
                        viewedCards = 0,
                        totalCards = reviewCount,
                        onClick = {
                            navController.navigate("review_screen")
                        }
                    )
                }
            }

            items(deckList){decks ->
                DeckCard(
                    deckName = decks.name,
                    viewedCards = decks.viewedCards,
                    totalCards = decks.totalCards,
                    onClick = {
                        navController.navigate("flashcard_screen/${decks.id}/${decks.name}")
                    }
                )

            }



        }
    }
}



@Preview
@Composable
fun DashboardPreview(){
    DashboardContent(
        navController = NavController(LocalContext.current),
        studiedCardCount = 12,
        deckCount = 4,
        reviewCount = 5,
        deckList = listOf(
            DeckSummary( 1, "History", 20, 10, 0),
            DeckSummary(2, "Arts", 15, 2, 1)
        ),
        username = "Test"
    )
}
