package com.example.flashcards_app.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AIResponse (
    @SerialName("question")
    val question: String,
    @SerialName("answer")
    val answer: String
)