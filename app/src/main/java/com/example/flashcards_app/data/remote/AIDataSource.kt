package com.example.flashcards_app.data.remote

import com.example.flashcards_app.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AIDataSource @Inject constructor(){
    suspend fun getFlashcardsFromText(text: String, deckName:String) : List<AIResponse>{
        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        val prompt = """
            You are a teacher. Create a list of flashcards from the following text. The list must have 5 questions and answers.
            Do not make the questions or answers too long.
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

        try {
            val response = generativeModel.generateContent(prompt)
            val rawJson = response.text ?: ""

            val cleanJson = rawJson.replace("```json", "").replace("```", "").trim()

            return Json.decodeFromString<List<AIResponse>>(cleanJson)
        }catch (e: Exception){
            e.printStackTrace()
            println("AI Error: ${e.message}")

            return emptyList()
        }
    }
}