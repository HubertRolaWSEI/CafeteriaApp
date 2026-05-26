package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.components.AppCard
import com.example.cafeteriaapp.components.ScreenContainer
import com.example.cafeteriaapp.data.RewardItem

@Composable
fun RewardsScreen(
    stamps: Int,
    rewards: List<RewardItem>,
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit,
    onClaimReward: (RewardItem) -> Unit
) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Nagrody")
            RewardsStatus(isLoading, errorMessage, onRefresh)
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                RewardColumn(rewards.take((rewards.size + 1) / 2), stamps, onClaimReward, Modifier.weight(1f))
                RewardColumn(rewards.drop((rewards.size + 1) / 2), stamps, onClaimReward, Modifier.weight(1f))
            }
        }
    } else {
        ScreenContainer("Nagrody") {
            RewardsStatus(isLoading, errorMessage, onRefresh)
            rewards.forEach { reward ->
                RewardCard(reward, stamps, onClaimReward)
            }
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    Text(
        title,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun RewardColumn(
    rewards: List<RewardItem>,
    stamps: Int,
    onClaimReward: (RewardItem) -> Unit,
    modifier: Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        rewards.forEach { reward ->
            RewardCard(reward, stamps, onClaimReward)
        }
    }
}

@Composable
private fun RewardsStatus(
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit
) {
    if (isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

    if (errorMessage != null) {
        AppCard {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            Button(onClick = onRefresh) {
                Text("Sprobuj ponownie")
            }
        }
    }
}

@Composable
private fun RewardCard(
    reward: RewardItem,
    stamps: Int,
    onClaimReward: (RewardItem) -> Unit
) {
    val canClaim = reward.requiredStamps > 0 && stamps >= reward.requiredStamps
    val statusText = when {
        reward.requiredStamps == 0 -> reward.status
        canClaim -> "Gotowa do odbioru"
        else -> "Brakuje ${reward.requiredStamps - stamps} pieczatek"
    }

    AppCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    reward.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    reward.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AssistChip(
                onClick = {},
                label = {
                    Text(
                        if (reward.requiredStamps > 0) "${reward.requiredStamps}" else "soon"
                    )
                },
                shape = RoundedCornerShape(50)
            )
        }

        StatusBadge(statusText, canClaim)

        if (canClaim) {
            Button(
                onClick = { onClaimReward(reward) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Odbierz")
            }
        }
    }
}

@Composable
private fun StatusBadge(text: String, positive: Boolean) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (positive) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            color = if (positive) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}