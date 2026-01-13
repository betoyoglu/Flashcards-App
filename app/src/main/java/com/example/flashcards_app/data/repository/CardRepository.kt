package com.example.flashcards_app.data.repository

import com.example.flashcards_app.data.local.dao.CardDao
import com.example.flashcards_app.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val cardDao: CardDao,
){
    suspend fun insertCard(card: CardEntity){
        return cardDao.insertCard(card)
    }
    suspend fun saveCards(cards: List<CardEntity>){
        return cardDao.insertCards(cards)
    }

    suspend fun deleteCard(card: CardEntity){
        return cardDao.deleteCard(card)
    }

    suspend fun updateCard(card: CardEntity){
        return cardDao.updateCard(card)
    }

    suspend fun updateCardStatus(card: CardEntity, isLearned: Boolean){
        val updatedCard = card.copy(
            isLearned = isLearned,
            isViewed = true
        )
        cardDao.updateCard(updatedCard)
    }

    fun getCardsByDeckId(deckId: Int) : Flow<List<CardEntity>>{
        return cardDao.getCardsByDeckId(deckId)
    }

    fun getAllCards() : Flow<List<CardEntity>>{
        return cardDao.getAllCards()
    }

    fun getCardById(id: Int) : CardEntity?{
        return cardDao.getCardById(id)
    }

    fun getCardsToReview(): Flow<List<CardEntity>>{
        return cardDao.getCardsToReview()
    }

    fun getStudiedCards() : Flow<List<CardEntity>>{
        return cardDao.getStudiedCards()
    }
}