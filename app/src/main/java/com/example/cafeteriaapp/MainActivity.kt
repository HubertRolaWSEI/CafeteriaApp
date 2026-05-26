package com.example.cafeteriaapp

import android.Manifest
import android.os.Bundle
import android.os.Build
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cafeteriaapp.notifications.CoffeeNotificationHelper
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cafeteriaapp.ui.theme.CafeteriaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CoffeeNotificationHelper.createNotificationChannel(this)
        requestNotificationPermissionIfNeeded()

        setContent {
            val viewModel: CafeteriaViewModel = viewModel()
            val preferences = viewModel.preferences.collectAsStateWithLifecycle()

            CafeteriaAppTheme(
                darkTheme = preferences.value.darkModeEnabled
            ) {
                CafeteriaApp(viewModel = viewModel)
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }
}
