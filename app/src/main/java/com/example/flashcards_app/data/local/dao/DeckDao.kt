package com.example.flashcards_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flashcards_app.data.local.entity.DeckEntity
import com.example.flashcards_app.data.model.DeckSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: DeckEntity) : Long

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)

    @Query("select * from decks order by id desc")
    fun getAllDecks() : Flow<List<DeckEntity>>

    @Query("select * from decks where id = :id")
    suspend fun getDeckById(id: Int) : DeckEntity?

    @Query("""
    SELECT 
        d.id as id, 
        d.name as name,
        COUNT(c.id) as totalCards,
        COUNT(CASE WHEN c.isViewed = 1 THEN 1 END) as viewedCards,
        COUNT(CASE WHEN c.isViewed = 1 AND c.isLearned = 0 THEN 1 END) as reviewCount
    FROM decks d
    LEFT JOIN cards c ON d.id = c.deckId
    GROUP BY d.id
    """)
    fun getDecksWithStats(): Flow<List<DeckSummary>>
}