package com.example.cafeteriaapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafeteriaapp.data.AppPreferences
import com.example.cafeteriaapp.data.AppPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CafeteriaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppPreferencesRepository(application)

    val preferences = repository.preferences.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppPreferences()
    )

    fun addStamp() {
        val currentStamps = preferences.value.stamps
        val newStamps = if (currentStamps < 8) currentStamps + 1 else 0

        viewModelScope.launch {
            repository.setStamps(newStamps)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setNotificationsEnabled(enabled)
        }
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDarkModeEnabled(enabled)
        }
    }
}