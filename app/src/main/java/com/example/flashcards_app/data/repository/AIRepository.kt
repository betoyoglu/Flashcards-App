package com.example.flashcards_app.data.repository

import com.example.flashcards_app.data.remote.AIDataSource
import javax.inject.Inject

class AIRepository @Inject constructor(
    private val aiDataSource: AIDataSource

){

}