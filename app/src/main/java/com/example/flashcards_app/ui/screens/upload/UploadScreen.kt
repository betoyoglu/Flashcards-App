package com.example.flashcards_app.ui.screens.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flashcards_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    onBackClick : () -> Unit,
    onSaveClick : () -> Unit,
    viewModel: UploadViewModel = hiltViewModel()
){
    val deckName by viewModel.deckName.collectAsStateWithLifecycle()
    val pdfUri by viewModel.pdfUri.collectAsStateWithLifecycle()
    val context = LocalContext.current

    //sadece pdf için file picker
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    )
    {uri : Uri? ->
        if(uri != null) {
            viewModel.onPdfSelected(uri, context)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Upload New Material",
                        fontFamily = FontFamily(Font(R.font.robotocondensed_regular))
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = deckName,
                onValueChange = {viewModel.onDeckNameChange(it)},
                label = {Text(text = "Deck Name:",
                              fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
                placeholder = {Text(text = "e.g. History of Art",
                                     fontFamily = FontFamily(Font(R.font.robotocondensed_regular)))},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        //pdf picker
                        pdfPickerLauncher.launch("application/pdf")
                    },
                contentAlignment = Alignment.Center
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if(pdfUri == null){
                        Icon(
                            painter = painterResource(R.drawable.uploadfile2) ,
                            contentDescription = "Upload",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to upload PDF",
                            color= MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily(Font(R.font.robotocondensed_regular))
                        )
                    }
                    else{
                        Icon(
                            painter = painterResource(R.drawable.selectedfile) ,
                            contentDescription = "Selected",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "PDF Selected!",
                            color= Color(0xFF4CAF50),
                            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = pdfUri?.lastPathSegment ?: "Unknown File",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.generateFlashcards()
                    onSaveClick
                },
                enabled = deckName.isNotBlank() && pdfUri !=null, //deck adı boş değilse ve pdf seçilmişse
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Generate Flashcards",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular))
                )
            }
        }
    }
}

@Preview
@Composable
fun UploadScreenPreview(){
    UploadScreen(
        onBackClick = {},
        onSaveClick = {}
    )
}