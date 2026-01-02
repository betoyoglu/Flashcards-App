package com.example.flashcards_app.ui.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()){
    val deckList by viewModel.decks.collectAsStateWithLifecycle()
    val studiedCards by viewModel.studiedCards.collectAsStateWithLifecycle()
    val reviewCards by viewModel.reviewCards.collectAsStateWithLifecycle()


}



@Preview
@Composable
fun DashboardPreview(){
    DashboardScreen()
}