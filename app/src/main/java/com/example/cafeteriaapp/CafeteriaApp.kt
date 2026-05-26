package com.example.cafeteriaapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cafeteriaapp.screens.*
import kotlinx.coroutines.delay

@Composable
fun CafeteriaApp() {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var selectedScreen by rememberSaveable { mutableStateOf("Karta") }
    var stamps by rememberSaveable { mutableIntStateOf(3) }

    LaunchedEffect(Unit) {
        delay(1200)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf("Karta", "Nagrody", "Menu", "Ustawienia").forEach { screen ->
                    NavigationBarItem(
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen },
                        icon = {},
                        label = { Text(screen) },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding),
            color = Color(0xFFF7F1EA)
        ) {
            when (selectedScreen) {
                "Karta" -> LoyaltyScreen(
                    stamps = stamps,
                    onAddStamp = { stamps = if (stamps < 8) stamps + 1 else 0 }
                )
                "Nagrody" -> RewardsScreen(stamps)
                "Menu" -> MenuScreen()
                "Ustawienia" -> SettingsScreen(
                    onOpenAuthors = { selectedScreen = "Autorzy" }
                )
                "Autorzy" -> AuthorsScreen()
            }
        }
    }
}