package com.example.ecommerce.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

private val LightColors = lightColorScheme(
    primary = Color(0xFF2874F0),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFDCE8FF),
    onPrimaryContainer = Color(0xFF001A41),

    secondary = Color(0xFFFFA000),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFF351000),

    tertiary = Color(0xFF00897B),
    onTertiary = Color.White,

    background = Color(0xFFF5F6F8),
    onBackground = Color(0xFF17181A),

    surface = Color.White,
    onSurface = Color(0xFF17181A),

    surfaceVariant = Color(0xFFE9EBEF),
    onSurfaceVariant = Color(0xFF555A63),

    outline = Color(0xFF8A8F98),

    error = Color(0xFFD32F2F),
    onError = Color.White,

    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)

)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7AA7FF),
    onPrimary = Color(0xFF002E6B),

    primaryContainer = Color(0xFF00449B),
    onPrimaryContainer = Color(0xFFD9E5FF),

    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFF4A2800),

    secondaryContainer = Color(0xFF6D3B00),
    onSecondaryContainer = Color(0xFFFFDDB8),

    tertiary = Color(0xFF4DB6AC),
    onTertiary = Color(0xFF003731),

    background = Color(0xFF101114),
    onBackground = Color(0xFFF1F1F3),

    surface = Color(0xFF191A1F),
    onSurface = Color(0xFFF1F1F3),

    surfaceVariant = Color(0xFF292B31),
    onSurfaceVariant = Color(0xFFC4C6CE),

    outline = Color(0xFF8E9099),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),

    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)

)

private val AppTypography = Typography(
    headlineLarge = androidx.compose.material3.Typography().headlineLarge.copy(
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = androidx.compose.material3.Typography().headlineSmall.copy(
        fontWeight = FontWeight.Bold
    ),
    titleLarge = androidx.compose.material3.Typography().titleLarge.copy(
        fontWeight = FontWeight.Bold
    ),
    titleMedium = androidx.compose.material3.Typography().titleMedium.copy(
        fontWeight = FontWeight.SemiBold
    ),
    labelLarge = androidx.compose.material3.Typography().labelLarge.copy(
        fontWeight = FontWeight.SemiBold
    )
)

@Composable
fun ECommerceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColors
        } else {
            LightColors
        },
        typography = AppTypography,
        content = content
    )
}