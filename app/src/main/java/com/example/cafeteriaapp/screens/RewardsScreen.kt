package com.example.cafeteriaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun RewardsScreen(stamps: Int) {
    ScreenContainer("Nagrody") {
        RewardCard("Darmowa kawa", "Dostepna po zebraniu 8 pieczatek.", if (stamps >= 8) "Gotowa do odbioru" else "Brakuje ${8 - stamps} pieczatek")
        RewardCard("Croissant -20%", "Planowana nagroda dla stalych klientow.", "Wkrotce")
        RewardCard("Podwojne punkty", "Promocja sezonowa w poniedzialki.", "Wkrotce")
    }
}

@Composable
private fun RewardCard(title: String, description: String, status: String) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(18.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(description, color = Color(0xFF6D4C41))
            Spacer(Modifier.height(10.dp))
            Text(status, fontWeight = FontWeight.Bold, color = Color(0xFF795548))
        }
    }
}