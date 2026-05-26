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
fun MenuScreen() {
    ScreenContainer("Menu kawiarni") {
        CafeItem("Espresso", "Intensywna kawa klasyczna", "8 zl")
        CafeItem("Cappuccino", "Espresso, mleko i delikatna pianka", "13 zl")
        CafeItem("Latte", "Lagodna kawa mleczna", "15 zl")
        CafeItem("Flat White", "Podwojne espresso z mlekiem", "16 zl")
        CafeItem("Sernik", "Domowe ciasto dnia", "14 zl")
    }
}

@Composable
private fun CafeItem(name: String, description: String, price: String) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(name, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(description, color = Color(0xFF6D4C41))
            }
            Text(price, fontWeight = FontWeight.Bold, color = Color(0xFF795548))
        }
    }
}