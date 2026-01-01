package com.example.flashcards_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcards_app.data.local.dao.CardDao
import com.example.flashcards_app.data.local.dao.DeckDao
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.data.local.entity.DeckEntity

@Database(entities = [DeckEntity:: class, CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val deckDao: DeckDao
    abstract val cardDao: CardDao

}