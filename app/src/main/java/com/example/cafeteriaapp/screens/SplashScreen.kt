package com.example.cafeteriaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.cafeteriaapp.components.AppLogo

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF3E2723)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo(104)
        Spacer(modifier = Modifier.height(20.dp))
        Text("CafeteriaApp", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Text("Program lojalnosciowy kawiarni", color = Color(0xFFD7CCC8), fontSize = 16.sp)
    }
}