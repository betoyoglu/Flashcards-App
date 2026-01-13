package com.example.flashcards_app.data.repository

import com.example.flashcards_app.data.local.dao.DeckDao
import com.example.flashcards_app.data.local.entity.DeckEntity
import com.example.flashcards_app.data.model.DeckSummary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val deckDao: DeckDao
){
    suspend fun insertDeck(name:String) : Long {
        return deckDao.insertDeck(DeckEntity(name=name))
    }

    suspend fun deleteDeck(deck: DeckEntity){
        return deckDao.deleteDeck(deck)
    }

    val getAllDecks : Flow<List<DeckEntity>> = deckDao.getAllDecks()

    suspend fun getDeckById(id: Int) : DeckEntity?{
        return deckDao.getDeckById(id)
    }

    fun getDecksWithStats(): Flow<List<DeckSummary>> {
        return deckDao.getDecksWithStats()
    }


}