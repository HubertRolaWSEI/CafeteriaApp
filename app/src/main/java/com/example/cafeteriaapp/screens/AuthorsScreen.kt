package com.example.cafeteriaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.components.AppLogo
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun AuthorsScreen() {
    ScreenContainer("Autorzy") {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo()
                Spacer(Modifier.height(16.dp))
                Text("CafeteriaApp", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Aplikacja lojalnosciowa - kawiarnia")
                Spacer(Modifier.height(22.dp))
                Text("Autor: wpisz swoje imie i nazwisko")
                Text("Grupa: wpisz swoja grupe")
                Spacer(Modifier.height(22.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFEEE7DF), RoundedCornerShape(12.dp)).padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("IDEIS", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF795548))
                }
                Text("Logo IDEIS - wersja demonstracyjna", textAlign = TextAlign.Center, color = Color(0xFF6D4C41))
            }
        }
    }
}