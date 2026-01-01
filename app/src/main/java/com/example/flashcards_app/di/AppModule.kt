package com.example.flashcards_app.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.flashcards_app.data.local.AppDatabase
import com.example.flashcards_app.data.local.dao.CardDao
import com.example.flashcards_app.data.local.dao.DeckDao

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //database
    @Provides
    @Singleton
    fun provideDatabase(app: Application) : AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "flashcard_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeckDao(db: AppDatabase) : DeckDao = db.deckDao

    @Provides
    @Singleton
    fun provideCardDao(db: AppDatabase) : CardDao= db.cardDao

    //ai
    /*
    @Provides
    @Singleton
    fun provideGeminiModel() : GenerativeModel {
        return GenerativeModel(modelName = "gemini-pro", apiKey = )
    }
    */
}