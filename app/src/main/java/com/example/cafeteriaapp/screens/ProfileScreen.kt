package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafeteriaapp.components.AppCard
import com.example.cafeteriaapp.components.AppLogo
import com.example.cafeteriaapp.components.ScreenContainer
import com.example.cafeteriaapp.data.ClaimItem

@Composable
fun ProfileScreen(
    customerName: String,
    stamps: Int,
    claims: List<ClaimItem>,
    onNameSave: (String) -> Unit
) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val claimedRewards = claims.size
    val loyaltyLevel = loyaltyLevelFor(claimedRewards)

    if (landscape) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Profil")
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                ProfileCard(customerName, loyaltyLevel, onNameSave, Modifier.weight(1f))
                StatsCard(stamps, claimedRewards, loyaltyLevel, Modifier.weight(1f))
            }
        }
    } else {
        ScreenContainer("Profil") {
            ProfileCard(customerName, loyaltyLevel, onNameSave)
            StatsCard(stamps, claimedRewards, loyaltyLevel)
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
private fun ProfileCard(
    customerName: String,
    loyaltyLevel: String,
    onNameSave: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var nameInput by remember { mutableStateOf(customerName) }

    LaunchedEffect(customerName) {
        nameInput = customerName
    }

    AppCard(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo(92)
            Spacer(Modifier.height(12.dp))
            Text(
                customerName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            LevelBadge(loyaltyLevel)
        }

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Imię klienta") },
            singleLine = true
        )

        Button(
            onClick = { onNameSave(nameInput.trim()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zapisz profil")
        }
    }
}

@Composable
private fun StatsCard(
    stamps: Int,
    claimedRewards: Int,
    loyaltyLevel: String,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Text(
            "Statystyki klienta",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        StatRow("Aktualne pieczątki", stamps.toString())
        StatRow("Odebrane nagrody", claimedRewards.toString())
        StatRow("Poziom lojalnościowy", loyaltyLevel)
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            value,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun LevelBadge(level: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = level,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

private fun loyaltyLevelFor(claimedRewards: Int): String {
    return when {
        claimedRewards >= 6 -> "VIP"
        claimedRewards >= 3 -> "Kawowy fan"
        claimedRewards >= 1 -> "Stały klient"
        else -> "Nowy klient"
    }
}
