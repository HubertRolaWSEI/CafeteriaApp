package com.example.cafeteriaapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CoffeeBrown,
    onPrimary = Cream,
    primaryContainer = ColorTokens.LightPrimaryContainer,
    onPrimaryContainer = CoffeeDark,
    secondary = Caramel,
    background = Cream,
    onBackground = CoffeeDark,
    surface = SoftSurface,
    onSurface = CoffeeDark,
    surfaceVariant = ColorTokens.LightSurfaceVariant,
    onSurfaceVariant = ColorTokens.LightOnSurfaceVariant,
    errorContainer = ColorTokens.LightErrorContainer,
    onErrorContainer = ColorTokens.LightOnErrorContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = Caramel,
    onPrimary = CoffeeDark,
    primaryContainer = CoffeeBrown,
    onPrimaryContainer = Cream,
    secondary = Caramel,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkCard,
    onSurface = DarkText,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = ColorTokens.DarkOnSurfaceVariant,
    errorContainer = ColorTokens.DarkErrorContainer,
    onErrorContainer = ColorTokens.DarkOnErrorContainer
)

private object ColorTokens {
    val LightPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFF3D7BF)
    val LightSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFF0E1D3)
    val LightOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF6B5A50)
    val LightErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6)
    val LightOnErrorContainer = androidx.compose.ui.graphics.Color(0xFF410002)

    val DarkOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFD8C2B3)
    val DarkErrorContainer = androidx.compose.ui.graphics.Color(0xFF93000A)
    val DarkOnErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6)
}

@Composable
fun CafeteriaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}