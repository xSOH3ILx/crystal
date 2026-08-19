package ir.marheil.crystal.feature.liabilities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.marheil.crystal.core.common.formatAmount
import ir.marheil.crystal.core.designsystem.components.GlassCard
import ir.marheil.crystal.core.designsystem.theme.*

data class LiabilityMock(
    val id: Long,
    val title: String,
    val totalAmount: Double,
    val remainingAmount: Double,
    val monthlyPayment: Double,
    val lender: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiabilitiesScreen(
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            LiabilityMock(1, "وام مسکن", 400000000.0, 260000000.0, 9500000.0, "بانک مسکن"),
            LiabilityMock(2, "قرض شخصی", 50000000.0, 20000000.0, 5000000.0, "صندوق خانوادگی")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("بدهی‌ها و اقساط وام", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add liability */ },
                containerColor = PrimaryEmerald,
                contentColor = DarkBackground
            ) {
                Icon(Icons.Default.Add, contentDescription = "افزودن بدهی")
            }
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
            items(items) { item ->
                GlassCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(item.lender, color = TextMuted, fontSize = 12.sp)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("مانده بدهی:", color = TextSecondary, fontSize = 13.sp)
                            Text("${formatAmount(item.remainingAmount)} ریال", color = LossRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("مبلغ قسط ماهانه:", color = TextSecondary, fontSize = 13.sp)
                            Text("${formatAmount(item.monthlyPayment)} ریال", color = TextPrimary, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}
