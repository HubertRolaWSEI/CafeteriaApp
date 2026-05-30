package com.example.cafeteriaapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore by preferencesDataStore(name = "app_preferences")
private const val MAX_LOCAL_CLAIMS = 50

data class AppPreferences(
    val stamps: Int = 3,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val customerName: String = "Klient kawiarni"
)

class AppPreferencesRepository(private val context: Context) {
    private object Keys {
        val STAMPS = intPreferencesKey("stamps")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
        val CUSTOMER_NAME = stringPreferencesKey("customer_name")
        val LOCAL_CLAIMS = stringPreferencesKey("local_claims")
    }

    val preferences: Flow<AppPreferences> = context.dataStore.data.map { prefs ->
        AppPreferences(
            stamps = prefs[Keys.STAMPS] ?: 3,
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            darkModeEnabled = prefs[Keys.DARK_MODE_ENABLED] ?: false,
            customerName = prefs[Keys.CUSTOMER_NAME] ?: "Klient kawiarni"
        )
    }

    val localClaims: Flow<List<ClaimItem>> = context.dataStore.data.map { prefs ->
        decodeClaims(prefs[Keys.LOCAL_CLAIMS].orEmpty())
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

    suspend fun setCustomerName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.CUSTOMER_NAME] = name
        }
    }

    suspend fun addLocalClaim(claim: ClaimItem) {
        context.dataStore.edit { prefs ->
            val currentClaims = decodeClaims(prefs[Keys.LOCAL_CLAIMS].orEmpty())
            val mergedClaims = mergeClaims(listOf(claim), currentClaims)
            prefs[Keys.LOCAL_CLAIMS] = encodeClaims(mergedClaims)
        }
    }

    suspend fun mergeLocalClaims(claims: List<ClaimItem>) {
        context.dataStore.edit { prefs ->
            val currentClaims = decodeClaims(prefs[Keys.LOCAL_CLAIMS].orEmpty())
            val mergedClaims = mergeClaims(claims, currentClaims)
            prefs[Keys.LOCAL_CLAIMS] = encodeClaims(mergedClaims)
        }
    }

    private fun encodeClaims(claims: List<ClaimItem>): String {
        val jsonArray = JSONArray()

        claims.take(MAX_LOCAL_CLAIMS).forEach { claim ->
            jsonArray.put(
                JSONObject()
                    .put("id", claim.id)
                    .put("rewardId", claim.rewardId)
                    .put("rewardTitle", claim.rewardTitle)
                    .put("usedStamps", claim.usedStamps)
                    .put("claimedAt", claim.claimedAt)
            )
        }

        return jsonArray.toString()
    }

    private fun decodeClaims(rawClaims: String): List<ClaimItem> {
        if (rawClaims.isBlank()) return emptyList()

        return runCatching {
            val jsonArray = JSONArray(rawClaims)
            List(jsonArray.length()) { index ->
                val item = jsonArray.getJSONObject(index)
                ClaimItem(
                    id = item.getInt("id"),
                    rewardId = item.getInt("rewardId"),
                    rewardTitle = item.getString("rewardTitle"),
                    usedStamps = item.getInt("usedStamps"),
                    claimedAt = item.getString("claimedAt")
                )
            }
        }.getOrDefault(emptyList())
    }

    private fun mergeClaims(
        newClaims: List<ClaimItem>,
        existingClaims: List<ClaimItem>
    ): List<ClaimItem> {
        return (newClaims + existingClaims)
            .distinctBy { claim ->
                "${claim.rewardId}|${claim.rewardTitle}|${claim.usedStamps}|${claim.claimedAt}"
            }
            .sortedByDescending { claim -> claim.claimedAt }
            .take(MAX_LOCAL_CLAIMS)
    }
}
