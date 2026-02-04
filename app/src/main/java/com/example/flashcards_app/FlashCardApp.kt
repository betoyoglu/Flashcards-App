package com.example.flashcards_app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlashCardApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PDFBoxResourceLoader.init(applicationContext)
    }

}