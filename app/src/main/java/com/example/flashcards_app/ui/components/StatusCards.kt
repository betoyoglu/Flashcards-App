package com.example.flashcards_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcards_app.R
import com.example.flashcards_app.ui.theme.darkOrange
import com.example.flashcards_app.ui.theme.darkPurple
import com.example.flashcards_app.ui.theme.orange
import com.example.flashcards_app.ui.theme.purple

@Composable
fun StatusCards(
    count: Int = 0,
    description: String = "Studied cards",
    containerColor: Color,
    contentColor: Color,
){
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        modifier = Modifier
            .size(width = 170.dp, height = 130.dp)
    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 13.dp, bottom = 8.dp)
        ){
            Text(
                text = count.toString(),
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 45.sp,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
fun PreviewStatusCard(){
    StatusCards(
    count= 12,
    description = "Studied cards",
    containerColor= purple,
    contentColor = darkPurple
    )
}