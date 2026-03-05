package com.example.flashcards_app.ui.screens.upload

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards_app.data.local.entity.CardEntity
import com.example.flashcards_app.data.repository.AIRepository
import com.example.flashcards_app.data.repository.CardRepository
import com.example.flashcards_app.data.repository.DeckRepository
import com.example.flashcards_app.util.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val AIrepository: AIRepository,
    private val deckRepository: DeckRepository,
    private val cardRepository: CardRepository) : ViewModel() {

    //deck adı
    private val _deckName = MutableStateFlow("")
    val deckName = _deckName.asStateFlow()

    //pdf uri'si
    private val _pdfUri = MutableStateFlow<Uri?>(null)
    val pdfUri = _pdfUri.asStateFlow()

    //geçici dosya - gemini'ya gidecek olan
    private var tempPdfFile : File? = null

    var isLoading by mutableStateOf(false)
        private set

    var showSuccessDialog by mutableStateOf(false)
        private set

    var newDeckId by mutableStateOf<Int?>(null)
        private set

    fun onDeckNameChange(newName:String){
        _deckName.value = newName
    }

    fun onPdfSelected(uri: Uri, context: Context){
        _pdfUri.value = uri

        viewModelScope.launch (Dispatchers.IO){
            tempPdfFile = uriToFile(context,uri)
        }
    }

    fun generateFlashcards(){
        val currentFile = tempPdfFile
        val deckNameVal = deckName.value

        if(currentFile == null || deckNameVal.isBlank()) return

        viewModelScope.launch {
            try {
                isLoading = true

                val aiResponseList = AIrepository.generateFlashcards(currentFile, deckNameVal)

               if(aiResponseList.isEmpty()){
                   println("AI returned empty list")
                   isLoading = false
                   return@launch
               }

                //deste olmadan cardları bir yere bağlayamayacağımızdan
                val newDeckIdValue = deckRepository.insertDeck(deckNameVal).toInt()

                //response'u cardlara dönüştür
                val cardEntities = aiResponseList.map { aiCard ->
                    CardEntity(
                        deckId = newDeckIdValue,
                        question = aiCard.question,
                        answer = aiCard.answer
                    )
                }

                cardRepository.saveCards(cardEntities)

                println("Done! ${cardEntities.size} cards created")

                isLoading = false
                newDeckId = newDeckIdValue
                showSuccessDialog = true

            } catch (e: Exception){
                e.printStackTrace()
                println("Hata oluştu: ${e.message}")
                isLoading = false
            }
        }
    }

    fun dismissDialog() {
        showSuccessDialog = false
    }
}

