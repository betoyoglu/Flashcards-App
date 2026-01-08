package com.example.flashcards_app.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

//uri'yi file'a dönüştüren fonksiyon. geçici olarak cache'de kaydetmek için
fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

        // Geçici bir dosya oluştur (Cache klasöründe)
        // "temp_pdf" isminde, ".pdf" uzantılı
        val tempFile = File.createTempFile("temp_uploaded", ".pdf", context.cacheDir)

        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}