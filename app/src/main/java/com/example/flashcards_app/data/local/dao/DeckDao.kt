package com.example.flashcards_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flashcards_app.data.local.entity.DeckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: DeckEntity)

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)

    @Query("select * from decks")
    fun getAllDecks() : Flow<List<DeckEntity>>

    @Query("select * from decks where id = :id")
    suspend fun getDeckById(id: Int) : DeckEntity?

}