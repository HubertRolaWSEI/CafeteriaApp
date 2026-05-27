package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Header(Modifier.weight(0.9f), compact = true)
            LoyaltyCard(
                stamps = stamps,
                onAddStamp = onAddStamp,
                modifier = Modifier.weight(1.5f),
                compact = true
            )
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(18.dp))
            Header()
            Spacer(Modifier.height(20.dp))
            LoyaltyCard(stamps, onAddStamp)
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier, compact: Boolean = false) {
    val logoSize = if (compact) 74 else 94
    val titleSize = if (compact) 24.sp else 32.sp
    val subtitleSize = if (compact) 13.sp else 16.sp

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AppLogo(logoSize)
        Spacer(Modifier.height(if (compact) 10.dp else 14.dp))
        Text(
            "CafeteriaApp",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            "Program lojalnościowy kawiarni",
            fontSize = subtitleSize,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoyaltyCard(
    stamps: Int,
    onAddStamp: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    val maxStamps = 8
    val progress = stamps / maxStamps.toFloat()
    val missing = (maxStamps - stamps).coerceAtLeast(0)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 550),
        label = "loyaltyProgress"
    )

    val cardPadding = if (compact) 16.dp else 22.dp
    val titleSize = if (compact) 18.sp else 22.sp
    val counterSize = if (compact) 22.sp else 30.sp
    val stampSize = if (compact) 40.dp else 52.dp
    val logoSize = if (compact) 29.dp else 38.dp
    val gap = if (compact) 9.dp else 14.dp

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier.padding(cardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Twoja karta kawowa",
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        if (missing == 0) "Nagroda gotowa do odbioru" else "Zbieraj pieczątki i odbieraj nagrody",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = if (compact) 12.sp else 14.sp
                    )
                }

                ProgressBadge("$stamps/$maxStamps")
            }

            Spacer(Modifier.height(gap))
            Text(
                "$stamps / $maxStamps pieczątek",
                fontSize = counterSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(gap))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(Modifier.height(gap))
            StampGrid(stamps, stampSize, logoSize)

            Spacer(Modifier.height(if (compact) 12.dp else 18.dp))
            Button(
                onClick = onAddStamp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (stamps < maxStamps) "Dodaj pieczątkę" else "Odbierz nagrodę")
            }

            Spacer(Modifier.height(8.dp))
            StatusText(missing)
        }
    }
}

@Composable
private fun ProgressBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun StatusText(missing: Int) {
    val text = if (missing == 0) {
        "Gratulacje! Możesz odebrać dostępną nagrodę."
    } else {
        "Brakuje jeszcze $missing pieczątek do darmowej kawy."
    }

    Text(
        text,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp
    )
}

@Composable
private fun StampGrid(stamps: Int, stampSize: Dp, logoSize: Dp) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                            .size(stampSize)
                            .graphicsLayer {
                                scaleX = stampScale
                                scaleY = stampScale
                            }
                            .clip(CircleShape)
                            .background(
                                if (active) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (active) {
                            Image(
                                painter = painterResource(id = R.drawable.cafe),
                                contentDescription = "Pieczątka",
                                modifier = Modifier
                                    .size(logoSize)
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
