package com.example.cafeteriaapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

data class AppPreferences(
    val stamps: Int = 3,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false
)

class AppPreferencesRepository(private val context: Context) {
    private object Keys {
        val STAMPS = intPreferencesKey("stamps")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
    }

    val preferences: Flow<AppPreferences> = context.dataStore.data.map { prefs ->
        AppPreferences(
            stamps = prefs[Keys.STAMPS] ?: 3,
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            darkModeEnabled = prefs[Keys.DARK_MODE_ENABLED] ?: false
        )
    }

    suspend fun setStamps(stamps: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.STAMPS] = stamps
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setDarkModeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DARK_MODE_ENABLED] = enabled
        }
    }
}