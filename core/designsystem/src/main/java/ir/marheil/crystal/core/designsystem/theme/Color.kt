package ir.marheil.crystal.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF0F172A)
val DarkSurface = Color(0xFF1E293B)
val DarkSurfaceVariant = Color(0xFF334155)

val PrimaryEmerald = Color(0xFF10B981)
val PrimaryBlue = Color(0xFF3B82F6)
val AccentPurple = Color(0xFF8B5CF6)
val DangerRed = Color(0xFFEF4444)
val WarningAmber = Color(0xFFF59E0B)

val TextPrimary = Color(0xFFF8FAFC)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryEmerald,
    secondary = PrimaryBlue,
    tertiary = AccentPurple,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = DangerRed
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryEmerald,
    secondary = PrimaryBlue,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A)
)
