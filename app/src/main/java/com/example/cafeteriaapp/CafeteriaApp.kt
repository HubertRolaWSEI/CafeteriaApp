package com.example.cafeteriaapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cafeteriaapp.screens.AuthorsScreen
import com.example.cafeteriaapp.screens.LoyaltyScreen
import com.example.cafeteriaapp.screens.MenuScreen
import com.example.cafeteriaapp.screens.RewardsScreen
import com.example.cafeteriaapp.screens.SettingsScreen
import com.example.cafeteriaapp.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private data class BottomNavItem(
    val route: String,
    val label: String,
    val iconRes: Int
)

@Composable
fun CafeteriaApp(viewModel: CafeteriaViewModel) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var selectedScreen by rememberSaveable { mutableStateOf("Karta") }

    val preferences by viewModel.preferences.collectAsStateWithLifecycle()

    val menuItems by viewModel.menuItems.collectAsStateWithLifecycle()
    val menuLoading by viewModel.menuLoading.collectAsStateWithLifecycle()
    val menuError by viewModel.menuError.collectAsStateWithLifecycle()

    val promotion by viewModel.promotion.collectAsStateWithLifecycle()
    val promotionLoading by viewModel.promotionLoading.collectAsStateWithLifecycle()
    val promotionError by viewModel.promotionError.collectAsStateWithLifecycle()

    val rewards by viewModel.rewards.collectAsStateWithLifecycle()
    val rewardsLoading by viewModel.rewardsLoading.collectAsStateWithLifecycle()
    val rewardsError by viewModel.rewardsError.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()

    val navItems = listOf(
        BottomNavItem("Karta", "Karta", R.drawable.ic_nav_card),
        BottomNavItem("Nagrody", "Nagrody", R.drawable.ic_nav_rewards),
        BottomNavItem("Menu", "Menu", R.drawable.ic_nav_menu),
        BottomNavItem("Ustawienia", "Opcje", R.drawable.ic_nav_settings)
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
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

                "Nagrody" -> RewardsScreen(
                    stamps = preferences.stamps,
                    rewards = rewards,
                    isLoading = rewardsLoading,
                    errorMessage = rewardsError,
                    onRefresh = viewModel::loadRewardsFromBackend,
                    onClaimReward = { reward ->
                        viewModel.claimReward(reward)
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar("Odebrano: ${reward.title}")
                        }
                    }
                )

                "Menu" -> MenuScreen(
                    items = menuItems,
                    promotion = promotion,
                    isLoading = menuLoading,
                    errorMessage = menuError,
                    isPromotionLoading = promotionLoading,
                    promotionError = promotionError,
                    onRefresh = viewModel::loadMenuFromBackend,
                    onRefreshPromotion = viewModel::loadPromotionFromBackend
                )

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
