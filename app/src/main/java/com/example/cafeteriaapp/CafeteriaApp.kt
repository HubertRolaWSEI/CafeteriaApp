package com.example.cafeteriaapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cafeteriaapp.screens.*
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: String
)

@Composable
fun CafeteriaApp(viewModel: CafeteriaViewModel) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var selectedScreen by rememberSaveable { mutableStateOf("Karta") }
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()

    val navItems = listOf(
        BottomNavItem("Karta", "Karta", "☕"),
        BottomNavItem("Nagrody", "Nagrody", "★"),
        BottomNavItem("Menu", "Menu", "≡"),
        BottomNavItem("Ustawienia", "Opcje", "⚙")
    )

    LaunchedEffect(Unit) {
        delay(1200)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = selectedScreen == item.route,
                        onClick = { selectedScreen = item.route },
                        icon = {
                            Text(
                                text = item.icon,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(item.label)
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (selectedScreen) {
                "Karta" -> LoyaltyScreen(
                    stamps = preferences.stamps,
                    onAddStamp = viewModel::addStamp
                )

                "Nagrody" -> RewardsScreen(preferences.stamps)

                "Menu" -> MenuScreen()

                "Ustawienia" -> SettingsScreen(
                    notifications = preferences.notificationsEnabled,
                    darkMode = preferences.darkModeEnabled,
                    onNotificationsChange = viewModel::setNotificationsEnabled,
                    onDarkModeChange = viewModel::setDarkModeEnabled,
                    onOpenAuthors = { selectedScreen = "Autorzy" }
                )

                "Autorzy" -> AuthorsScreen()
            }
        }
    }
}