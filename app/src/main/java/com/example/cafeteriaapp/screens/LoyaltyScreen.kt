package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.R
import com.example.cafeteriaapp.components.AppLogo

@Composable
fun LoyaltyScreen(stamps: Int, onAddStamp: () -> Unit) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Header(Modifier.weight(1f))
            LoyaltyCard(stamps, onAddStamp, Modifier.weight(1.3f))
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(28.dp))
            Header()
            Spacer(Modifier.height(28.dp))
            LoyaltyCard(stamps, onAddStamp)
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AppLogo()
        Spacer(Modifier.height(16.dp))
        Text(
            "CafeteriaApp",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            "Program lojalnosciowy kawiarni",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )
    }
}

@Composable
private fun LoyaltyCard(stamps: Int, onAddStamp: () -> Unit, modifier: Modifier = Modifier) {
    val maxStamps = 8
    val progress = stamps / maxStamps.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 550),
        label = "loyaltyProgress"
    )

    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Twoja karta kawowa",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "$stamps / $maxStamps pieczatek",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(20.dp))
            StampGrid(stamps)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onAddStamp) {
                Text(if (stamps < maxStamps) "Dodaj pieczatke" else "Odbierz nagrode")
            }
            Text(
                if (stamps < maxStamps) "Zbierz 8 pieczatek i odbierz darmowa kawe."
                else "Gratulacje! Darmowa kawa czeka na odbior.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StampGrid(stamps: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(4) { column ->
                    val active = row * 4 + column < stamps
                    val stampScale by animateFloatAsState(
                        targetValue = if (active) 1f else 0.82f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "stampScale"
                    )

                    Box(
                        Modifier
                            .size(52.dp)
                            .graphicsLayer {
                                scaleX = stampScale
                                scaleY = stampScale
                            }
                            .clip(CircleShape)
                            .background(
                                if (active) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (active) {
                            Image(
                                painter = painterResource(id = R.drawable.cafe),
                                contentDescription = "Pieczatka",
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}