package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.components.AppCard
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun SettingsScreen(
    notifications: Boolean,
    darkMode: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onOpenAuthors: () -> Unit
) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Konfiguracja")
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    PreferencesCard(notifications, darkMode, onNotificationsChange, onDarkModeChange)
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    AppInfoCard(onOpenAuthors)
                    PlannedOptionsCard()
                }
            }
        }
    } else {
        ScreenContainer("Konfiguracja") {
            PreferencesCard(notifications, darkMode, onNotificationsChange, onDarkModeChange)
            AppInfoCard(onOpenAuthors)
            PlannedOptionsCard()
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
private fun PreferencesCard(
    notifications: Boolean,
    darkMode: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onDarkModeChange: (Boolean) -> Unit
) {
    AppCard {
        SectionTitle("Preferencje")
        SettingRow("Powiadomienia o promocjach", "Informacje o nagrodach i odbiorze", notifications, onNotificationsChange)
        HorizontalDivider()
        SettingRow("Tryb ciemny", "Zmien wyglad aplikacji", darkMode, onDarkModeChange)
    }
}

@Composable
private fun AppInfoCard(onOpenAuthors: () -> Unit) {
    AppCard {
        SectionTitle("Aplikacja")
        Text("CafeteriaApp MVP", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(onClick = onOpenAuthors, modifier = Modifier.fillMaxWidth()) {
            Text("Informacje o autorach")
        }
    }
}

@Composable
private fun PlannedOptionsCard() {
    AppCard {
        SectionTitle("Planowane opcje")
        Text("Wybor ulubionej kawiarni", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Polaczenie z kontem klienta", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Historia zamowien", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
private fun SettingRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}