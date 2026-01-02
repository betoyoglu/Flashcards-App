package com.example.flashcards_app.data.repository

import com.example.flashcards_app.data.local.dao.DeckDao
import com.example.flashcards_app.data.local.entity.DeckEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val deckDao: DeckDao
){
    suspend fun insertDeck(deck: DeckEntity){
        return deckDao.insertDeck(deck)
    }

    suspend fun deleteDeck(deck: DeckEntity){
        return deckDao.deleteDeck(deck)
    }

    fun getAllDecks() : Flow<List<DeckEntity>>{
        return deckDao.getAllDecks()
    }

    suspend fun getDeckById(id: Int) : DeckEntity?{
        return deckDao.getDeckById(id)
    }
}