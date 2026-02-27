package com.example.flashcards_app.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.data.local.entity.DeckEntity
import com.example.flashcards_app.data.model.DeckSummary
import com.example.flashcards_app.data.repository.CardRepository
import com.example.flashcards_app.data.repository.DeckRepository
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val cardRepository: CardRepository, private val deckRepository: DeckRepository) : ViewModel() {
    //hello username
    private val _username = MutableStateFlow("Loading...")
    val username = _username.asStateFlow()

    //all decks
    private val _decks = MutableStateFlow<List<DeckSummary>>(emptyList())
    val decks = _decks.asStateFlow()

    //all cards
    private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val cards = _cards.asStateFlow()

    //studied cards
    private val _studiedCards = MutableStateFlow<List<CardEntity>>(emptyList())
    val studiedCards = _studiedCards.asStateFlow()

    //need to review
    private val _reviewCards = MutableStateFlow<List<CardEntity>>(emptyList())
    val reviewCards = _reviewCards.asStateFlow()


    init {
        fetchUsername()
        fetchDecks()
        fetchStudiedCards()
        fetchReviewCards()
        fetchCards()
    }

    private fun fetchUsername() {
        val auth = com.google.firebase.Firebase.auth
        val db = com.google.firebase.Firebase.firestore
        val currentUser = auth.currentUser

        if (currentUser != null) {
            viewModelScope.launch {
                try {
                    db.collection("users").document(currentUser.uid)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document != null && document.exists()) {
                                _username.value = document.getString("username") ?: "User"
                            }
                        }
                        .addOnFailureListener { e ->
                            _username.value = "Error"
                            e.printStackTrace()
                        }
                } catch (e: Exception) {
                    _username.value = "Connection Error"
                }
            }
        } else {
            _username.value = "Guest"
        }
    }

    private fun fetchDecks() {
        viewModelScope.launch {
            try {
                deckRepository.getDecksWithStats().collect { decks ->
                    _decks.value = decks
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchStudiedCards() {
        viewModelScope.launch {
            cardRepository.getStudiedCards().collect { cards ->
                _studiedCards.value = cards
            }
        }
    }

    private fun fetchReviewCards() {
        viewModelScope.launch {
            cardRepository.getCardsToReview().collect { cards ->
                _reviewCards.value = cards
            }
        }
    }

    private fun fetchCards() {
        viewModelScope.launch {
            cardRepository.getAllCards().collect { cards ->
                _cards.value = cards
            }
        }
    }
}