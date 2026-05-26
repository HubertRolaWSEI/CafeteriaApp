package com.example.cafeteriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cafeteriaapp.ui.theme.CafeteriaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
}