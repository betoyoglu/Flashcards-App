package com.example.flashcards_app.ui.screens.upload

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards_app.data.repository.AIRepository
import com.example.flashcards_app.util.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(private val repository: AIRepository) : ViewModel() {

    //deck adı
    private val _deckName = MutableStateFlow("")
    val deckName = _deckName.asStateFlow()

    //pdf uri'si
    private val _pdfUri = MutableStateFlow<Uri?>(null)
    val pdfUri = _pdfUri.asStateFlow()

    //geçici dosya - gemini'ya gidecek olan
    private var tempPdfFile : File? = null

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
                val response = repository.generateFlashcards(currentFile, deckNameVal)

                println("Gemini cevabı: $response")

            } catch (e: Exception){
                e.printStackTrace()
                println("Hata oluştu: ${e.message}")
            }
        }
    }
}

