package com.example.flashcards_app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = DeckEntity::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE // deck silinirse cardlar da silinsin
        )
    ])
data class CardEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deckId: Int,
    val question: String,
    val answer: String,
    val isLearned: Boolean = false
)