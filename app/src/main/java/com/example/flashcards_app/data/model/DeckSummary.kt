package com.example.flashcards_app.data.model

//progress bar için gerekli
data class DeckSummary(
    val id: Int,
    val name: String,
    val totalCards: Int,
    val viewedCards: Int,
    val reviewCount: Int
)