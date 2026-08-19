package ir.marheil.crystal.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.marheil.crystal.core.designsystem.components.GlassCard
import ir.marheil.crystal.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تنظیمات و سفارشی‌سازی", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("پایگاه داده و پشتیبان‌گیری", color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            item {
                SettingsItem(
                    icon = Icons.Default.CloudSync,
                    title = "پشتیبان‌گیری ابری و محلی",
                    subtitle = "خروجی گرفتن و بازیابی فایل SQLite"
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.CurrencyExchange,
                    title = "ارز مرجع اصلی",
                    subtitle = "تومان (IRR) / تتر (USDT)"
                )
            }
            item {
                Text("امنیت و حریم خصوصی", color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            item {
                SettingsItem(
                    icon = Icons.Default.Fingerprint,
                    title = "قفل بیومتریک / اثر انگشت",
                    subtitle = "ورود امن به اپلیکیشن کریستال"
                )
            }
            item {
                Text("درباره کریستال", color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            item {
                GlassCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("کریستال (Crystal) - دستیار هوشمند مالی", color = PrimaryEmerald, fontWeight = FontWeight.Bold)
                        Text("نسخه: ۱.۰.۰ (توسعه‌یافته با Kotlin و Compose)", color = TextMuted, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    GlassCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryEmerald)
            Column {
                Text(title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text(subtitle, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}
