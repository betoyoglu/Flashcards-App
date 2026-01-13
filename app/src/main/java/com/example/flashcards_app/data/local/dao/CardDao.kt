package com.example.flashcards_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flashcards_app.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Delete
    suspend fun deleteCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity) //swipe için

    @Query("SELECT * FROM cards WHERE deckId = :deckId")
    fun getCardsByDeckId(deckId: Int): Flow<List<CardEntity>>

    @Query("select * from cards")
    fun getAllCards() : Flow<List<CardEntity>>

    @Query("select * from cards where id = :id")
    fun getCardById(id: Int) : CardEntity?

    @Query("SELECT * FROM cards WHERE isViewed = 1 and isLearned = 0")
    fun getCardsToReview(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE isLearned = 1")
    fun getStudiedCards() : Flow<List<CardEntity>>
}