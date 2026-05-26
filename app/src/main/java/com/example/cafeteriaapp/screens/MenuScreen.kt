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
import com.example.cafeteriaapp.data.CafeMenuItem
import com.example.cafeteriaapp.data.PromotionItem

@Composable
fun MenuScreen(
    items: List<CafeMenuItem>,
    promotion: PromotionItem,
    isLoading: Boolean,
    errorMessage: String?,
    isPromotionLoading: Boolean,
    promotionError: String?,
    onRefresh: () -> Unit,
    onRefreshPromotion: () -> Unit
) {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Menu kawiarni")
            MenuStatus(isLoading, errorMessage, onRefresh)
            PromotionStatus(isPromotionLoading, promotionError, onRefreshPromotion)
            PromotionCard(promotion)
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                MenuColumn(items.take((items.size + 1) / 2), Modifier.weight(1f))
                MenuColumn(items.drop((items.size + 1) / 2), Modifier.weight(1f))
            }
        }
    } else {
        ScreenContainer("Menu kawiarni") {
            MenuStatus(isLoading, errorMessage, onRefresh)
            PromotionStatus(isPromotionLoading, promotionError, onRefreshPromotion)
            PromotionCard(promotion)
            items.forEach { CafeItem(it) }
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
private fun MenuColumn(items: List<CafeMenuItem>, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        items.forEach { CafeItem(it) }
    }
}

@Composable
private fun MenuStatus(isLoading: Boolean, errorMessage: String?, onRefresh: () -> Unit) {
    if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

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
private fun PromotionStatus(
    isLoading: Boolean,
    errorMessage: String?,
    onRefresh: () -> Unit
) {
    if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

    if (errorMessage != null) {
        AppCard {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            OutlinedButton(onClick = onRefresh) {
                Text("Odswiez promocje")
            }
        }
    }
}

@Composable
private fun PromotionCard(promotion: PromotionItem) {
    AppCard {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    promotion.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    promotion.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (promotion.code.isNotBlank()) {
                PromoCodeBadge(promotion.code)
            }
        }
    }
}

@Composable
private fun CafeItem(item: CafeMenuItem) {
    AppCard {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    item.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    item.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            PriceBadge(item.price)
        }
    }
}

@Composable
private fun PromoCodeBadge(code: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = code,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun PriceBadge(price: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = price,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}
