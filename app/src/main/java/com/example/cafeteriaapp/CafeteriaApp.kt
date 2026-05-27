package com.example.cafeteriaapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cafeteriaapp.screens.AuthorsScreen
import com.example.cafeteriaapp.screens.LoyaltyScreen
import com.example.cafeteriaapp.screens.MenuScreen
import com.example.cafeteriaapp.screens.ProfileScreen
import com.example.cafeteriaapp.screens.RewardsScreen
import com.example.cafeteriaapp.screens.SettingsScreen
import com.example.cafeteriaapp.screens.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

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
    val claims by viewModel.claims.collectAsStateWithLifecycle()
    val claimsLoading by viewModel.claimsLoading.collectAsStateWithLifecycle()
    val claimsError by viewModel.claimsError.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val context = LocalContext.current

    val navItems = listOf(
        BottomNavItem("Karta", "Karta", R.drawable.ic_nav_card),
        BottomNavItem("Nagrody", "Nagrody", R.drawable.ic_nav_rewards),
        BottomNavItem("Menu", "Menu", R.drawable.ic_nav_menu),
        BottomNavItem("Profil", "Profil", R.drawable.ic_nav_profile),
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

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var lastShakeTime = 0L

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val movement = maxOf(
                    kotlin.math.abs(x),
                    kotlin.math.abs(y),
                    kotlin.math.abs(z)
                )
                val now = System.currentTimeMillis()

                if (movement > 12f && now - lastShakeTime > 1800L) {
                    lastShakeTime = now
                    viewModel.loadPromotionFromBackend()
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar("Potrząśnięcie: odświeżam promocję dnia")
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        if (accelerometer != null) {
            sensorManager.registerListener(
                listener,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
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
                    claims = claims,
                    claimsLoading = claimsLoading,
                    claimsError = claimsError,
                    onRefresh = viewModel::loadRewardsFromBackend,
                    onRefreshClaims = viewModel::loadClaimsFromBackend,
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

                "Profil" -> ProfileScreen(
                    customerName = preferences.customerName,
                    stamps = preferences.stamps,
                    claims = claims,
                    onNameSave = { name ->
                        viewModel.setCustomerName(name)
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar("Profil zapisany")
                        }
                    }
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
