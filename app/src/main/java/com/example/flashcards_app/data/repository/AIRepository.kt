package com.example.flashcards_app.data.repository

import com.example.flashcards_app.data.remote.AIDataSource
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AIRepository @Inject constructor(
    private val aiDataSource: AIDataSource

){
    suspend fun generateFlashcards(file: File, deckName:String) : String = withContext(Dispatchers.IO){
        val pdfText = extractTextFromPDF(file)

        if (pdfText.isBlank()) {
            return@withContext "Hata: PDF içeriği boş veya okunamadı."
        }

        // 2. Metni AI'a gönder
        return@withContext aiDataSource.getFlashcardsFromText(pdfText, deckName)
    }

    private fun extractTextFromPDF(file: File) : String{
        return try {
            val document = PDDocument.load(file) //dosyayı al
            val stripper = PDFTextStripper() //textine ayır
            val text = stripper.getText(document)
            document.close()
            text
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}