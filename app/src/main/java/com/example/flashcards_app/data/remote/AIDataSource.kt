package com.example.flashcards_app.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.example.flashcards_app.BuildConfig
import javax.inject.Inject

class AIDataSource @Inject constructor(){
    suspend fun getFlashcardsFromText(text: String, deckName:String) : String{
        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        val prompt = """
            You are a teacher. Create a list of flashcards from the following text. The list must have 10 questions and answers.
            The text is about: $deckName
            
            Return the output strictly in the following JSON format (no markdown, no backticks):
            [
              {
                "question": "Question here",
                "answer": "Answer here"
              },
              ...
            ]
            
            Here is the text:
            $text
        """.trimIndent()

        val response = generativeModel.generateContent(prompt)
        return response.text ?: ""
    }
}