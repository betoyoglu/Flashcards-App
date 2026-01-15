package com.example.flashcards_app.ui.screens.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.data.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardViewModel @Inject constructor(private val cardRepository: CardRepository) : ViewModel(){

    //ekranda gösterilecek cardlar
    private val _uiState = MutableStateFlow<List<CardEntity>>(emptyList())
    val uiState = _uiState.asStateFlow()

    //hangi deste olduğunu bilmek için
    fun loadDeck(deckId: Int){
        viewModelScope.launch {
            val cards = cardRepository.getCardsByDeckId(deckId).first()  //veriyi yalnızca bir kere almak için
                _uiState.value = cards

        }
    }

    //sağa kaydırılırsa
    fun markAsLearned(card: CardEntity){
        viewModelScope.launch {
            removeCardFromList(card)
            cardRepository.updateCardStatus(card, isLearned = true)
        }
    }

    //sola kaydırılırsa
    fun markAsReview(card: CardEntity){
        viewModelScope.launch {
            removeCardFromList(card)
            cardRepository.updateCardStatus(card, isLearned = false)
        }
    }

    private fun removeCardFromList(card: CardEntity){
        _uiState.value = _uiState.value.filter { it.id != card.id }
    }



}