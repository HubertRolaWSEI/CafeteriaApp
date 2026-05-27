package com.example.cafeteriaapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cafeteriaapp.data.AppPreferences
import com.example.cafeteriaapp.data.AppPreferencesRepository
import com.example.cafeteriaapp.data.CafeMenuItem
import com.example.cafeteriaapp.data.ClaimItem
import com.example.cafeteriaapp.data.PromotionItem
import com.example.cafeteriaapp.data.RewardItem
import com.example.cafeteriaapp.network.ClaimsApiClient
import com.example.cafeteriaapp.network.MenuApiClient
import com.example.cafeteriaapp.network.PromotionApiClient
import com.example.cafeteriaapp.network.RewardsApiClient
import com.example.cafeteriaapp.notifications.CoffeeNotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CafeteriaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppPreferencesRepository(application)
    private val menuApiClient = MenuApiClient()
    private val promotionApiClient = PromotionApiClient()
    private val rewardsApiClient = RewardsApiClient()
    private val claimsApiClient = ClaimsApiClient()

    val preferences = repository.preferences.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppPreferences()
    )

    private val _menuItems = MutableStateFlow(defaultMenuItems())
    val menuItems: StateFlow<List<CafeMenuItem>> = _menuItems

    private val _menuLoading = MutableStateFlow(false)
    val menuLoading: StateFlow<Boolean> = _menuLoading

    private val _menuError = MutableStateFlow<String?>(null)
    val menuError: StateFlow<String?> = _menuError

    private val _promotion = MutableStateFlow(defaultPromotion())
    val promotion: StateFlow<PromotionItem> = _promotion

    private val _promotionLoading = MutableStateFlow(false)
    val promotionLoading: StateFlow<Boolean> = _promotionLoading

    private val _promotionError = MutableStateFlow<String?>(null)
    val promotionError: StateFlow<String?> = _promotionError

    private val _rewards = MutableStateFlow(defaultRewards())
    val rewards: StateFlow<List<RewardItem>> = _rewards

    private val _rewardsLoading = MutableStateFlow(false)
    val rewardsLoading: StateFlow<Boolean> = _rewardsLoading

    private val _rewardsError = MutableStateFlow<String?>(null)
    val rewardsError: StateFlow<String?> = _rewardsError

    private val _claims = MutableStateFlow<List<ClaimItem>>(emptyList())
    val claims: StateFlow<List<ClaimItem>> = _claims

    private val _claimsLoading = MutableStateFlow(false)
    val claimsLoading: StateFlow<Boolean> = _claimsLoading

    private val _claimsError = MutableStateFlow<String?>(null)
    val claimsError: StateFlow<String?> = _claimsError

    init {
        loadMenuFromBackend()
        loadPromotionFromBackend()
        loadRewardsFromBackend()
        loadClaimsFromBackend()
    }

    fun loadMenuFromBackend() {
        viewModelScope.launch {
            _menuLoading.value = true
            _menuError.value = null

            try {
                val menu = withContext(Dispatchers.IO) {
                    menuApiClient.fetchMenu()
                }
                _menuItems.value = menu
            } catch (e: Exception) {
                _menuError.value = "Nie udało się pobrać menu z backendu"
                _menuItems.value = defaultMenuItems()
            } finally {
                _menuLoading.value = false
            }
        }
    }

    fun loadPromotionFromBackend() {
        viewModelScope.launch {
            _promotionLoading.value = true
            _promotionError.value = null

            try {
                val promotionFromApi = withContext(Dispatchers.IO) {
                    promotionApiClient.fetchPromotion()
                }
                _promotion.value = promotionFromApi
            } catch (e: Exception) {
                _promotionError.value = "Nie udało się pobrać promocji dnia"
                _promotion.value = defaultPromotion()
            } finally {
                _promotionLoading.value = false
            }
        }
    }

    fun loadRewardsFromBackend() {
        viewModelScope.launch {
            _rewardsLoading.value = true
            _rewardsError.value = null

            try {
                val rewardsFromApi = withContext(Dispatchers.IO) {
                    rewardsApiClient.fetchRewards()
                }
                _rewards.value = rewardsFromApi
            } catch (e: Exception) {
                _rewardsError.value = "Nie udało się pobrać nagród z backendu"
                _rewards.value = defaultRewards()
            } finally {
                _rewardsLoading.value = false
            }
        }
    }

    fun loadClaimsFromBackend() {
        viewModelScope.launch {
            _claimsLoading.value = true
            _claimsError.value = null

            try {
                val claimsFromApi = withContext(Dispatchers.IO) {
                    claimsApiClient.fetchClaims()
                }
                _claims.value = claimsFromApi
            } catch (e: Exception) {
                _claimsError.value = "Nie udało się pobrać historii odbiorów"
            } finally {
                _claimsLoading.value = false
            }
        }
    }

    fun addStamp() {
        val currentStamps = preferences.value.stamps
        val newStamps = if (currentStamps < 8) currentStamps + 1 else 0

        viewModelScope.launch {
            repository.setStamps(newStamps)

            if (preferences.value.notificationsEnabled) {
                _rewards.value
                    .filter { reward ->
                        reward.requiredStamps > 0 &&
                                reward.requiredStamps == newStamps
                    }
                    .forEach { reward ->
                        CoffeeNotificationHelper.showRewardReadyNotification(
                            context = getApplication(),
                            rewardTitle = reward.title,
                            requiredStamps = reward.requiredStamps
                        )
                    }
            }
        }
    }

    fun claimReward(reward: RewardItem) {
        val currentStamps = preferences.value.stamps

        if (reward.requiredStamps <= 0 || currentStamps < reward.requiredStamps) {
            return
        }

        val newStamps = currentStamps - reward.requiredStamps

        viewModelScope.launch {
            repository.setStamps(newStamps)

            try {
                val claim = withContext(Dispatchers.IO) {
                    claimsApiClient.createClaim(reward)
                }
                _claims.value = listOf(claim) + _claims.value.filterNot { it.id == claim.id }
                _claimsError.value = null
            } catch (e: Exception) {
                _claimsError.value = "Nagroda odebrana lokalnie, ale backend historii jest niedostępny"
            }

            if (preferences.value.notificationsEnabled) {
                CoffeeNotificationHelper.showRewardClaimedNotification(
                    context = getApplication(),
                    rewardTitle = reward.title
                )
            }
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

    fun setCustomerName(name: String) {
        viewModelScope.launch {
            repository.setCustomerName(name.ifBlank { "Klient kawiarni" })
        }
    }

    private fun defaultMenuItems(): List<CafeMenuItem> = listOf(
        CafeMenuItem("Espresso", "Intensywna kawa klasyczna", "8 zł"),
        CafeMenuItem("Cappuccino", "Espresso, mleko i delikatna pianka", "13 zł"),
        CafeMenuItem("Latte", "Łagodna kawa mleczna", "15 zł"),
        CafeMenuItem("Flat White", "Podwójne espresso z mlekiem", "16 zł"),
        CafeMenuItem("Sernik", "Domowe ciasto dnia", "14 zł")
    )

    private fun defaultPromotion(): PromotionItem = PromotionItem(
        title = "Promocja dnia",
        description = "Latte i croissant w zestawie taniej",
        validToday = true,
        code = "LOCAL"
    )

    private fun defaultRewards(): List<RewardItem> = listOf(
        RewardItem(1, "Darmowa kawa", "Dostępna po zebraniu 8 pieczątek.", 8, "Aktywna"),
        RewardItem(2, "Croissant -20%", "Planowana nagroda dla stałych klientów.", 4, "Wkrótce"),
        RewardItem(3, "Podwójne punkty", "Promocja sezonowa w poniedziałki.", 0, "Wkrótce")
    )
}
