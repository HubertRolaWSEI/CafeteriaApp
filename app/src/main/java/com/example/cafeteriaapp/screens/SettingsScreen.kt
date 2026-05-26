package com.example.cafeteriaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun SettingsScreen(onOpenAuthors: () -> Unit) {
    var notifications by rememberSaveable { mutableStateOf(true) }
    var darkMode by rememberSaveable { mutableStateOf(false) }

    ScreenContainer("Konfiguracja") {
        SettingRow("Powiadomienia o promocjach", notifications) { notifications = it }
        SettingRow("Tryb ciemny", darkMode) { darkMode = it }

        Button(onClick = onOpenAuthors, modifier = Modifier.fillMaxWidth()) {
            Text("Informacje o autorach")
        }

        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.padding(18.dp)) {
                Text("Planowane opcje", fontWeight = FontWeight.Bold)
                Text("Wybor ulubionej kawiarni")
                Text("Polaczenie z kontem klienta")
                Text("Historia zamowien")
            }
        }
    }
}

@Composable
private fun SettingRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(title, Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}