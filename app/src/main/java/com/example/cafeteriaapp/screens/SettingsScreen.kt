package com.example.cafeteriaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun SettingsScreen(
    notifications: Boolean,
    darkMode: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onOpenAuthors: () -> Unit
) {
    ScreenContainer("Konfiguracja") {
        SettingRow("Powiadomienia o promocjach", notifications, onNotificationsChange)
        SettingRow("Tryb ciemny", darkMode, onDarkModeChange)

        Button(onClick = onOpenAuthors, modifier = Modifier.fillMaxWidth()) {
            Text("Informacje o autorach")
        }

        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(18.dp)) {
                Text("Planowane opcje", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text("Wybor ulubionej kawiarni", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Polaczenie z kontem klienta", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Historia zamowien", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun SettingRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, Modifier.weight(1f), fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}