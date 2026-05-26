package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.components.AppLogo

@Composable
fun LoyaltyScreen(stamps: Int, onAddStamp: () -> Unit) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Row(Modifier.fillMaxSize().padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Header(Modifier.weight(1f))
            LoyaltyCard(stamps, onAddStamp, Modifier.weight(1.3f))
        }
    } else {
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(28.dp))
            Header()
            Spacer(Modifier.height(28.dp))
            LoyaltyCard(stamps, onAddStamp)
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AppLogo()
        Spacer(Modifier.height(16.dp))
        Text("CafeteriaApp", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3E2723))
        Text("Program lojalnosciowy kawiarni", fontSize = 16.sp, color = Color(0xFF6D4C41))
    }
}

@Composable
private fun LoyaltyCard(stamps: Int, onAddStamp: () -> Unit, modifier: Modifier = Modifier) {
    val maxStamps = 8
    val progress = stamps / maxStamps.toFloat()

    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Twoja karta kawowa", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("$stamps / $maxStamps pieczatek", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF795548))
            Spacer(Modifier.height(16.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(8.dp)))
            Spacer(Modifier.height(20.dp))
            StampGrid(stamps)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onAddStamp) {
                Text(if (stamps < maxStamps) "Dodaj pieczatke" else "Odbierz nagrode")
            }
            Text(
                if (stamps < maxStamps) "Zbierz 8 pieczatek i odbierz darmowa kawe."
                else "Gratulacje! Darmowa kawa czeka na odbior.",
                textAlign = TextAlign.Center,
                color = Color(0xFF6D4C41)
            )
        }
    }
}

@Composable
private fun StampGrid(stamps: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(4) { column ->
                    val active = row * 4 + column < stamps
                    Box(
                        Modifier.size(52.dp).clip(CircleShape).background(if (active) Color(0xFF795548) else Color(0xFFD7CCC8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (active) "CA" else "", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}