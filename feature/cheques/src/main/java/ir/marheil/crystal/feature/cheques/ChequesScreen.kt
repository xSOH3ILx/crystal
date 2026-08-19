package ir.marheil.crystal.feature.cheques

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.marheil.crystal.core.common.formatAmount
import ir.marheil.crystal.core.designsystem.components.GlassCard
import ir.marheil.crystal.core.designsystem.theme.*

data class ChequeMock(
    val id: Long,
    val chequeNumber: String,
    val bankName: String,
    val amount: Double,
    val dueDate: String,
    val isPayable: Boolean,
    val partyName: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChequesScreen(
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            ChequeMock(1, "123456789", "بانک ملت", 85000000.0, "1403/12/25", true, "شرکت بازرگانی پارس"),
            ChequeMock(2, "987654321", "بانک صادرات", 140000000.0, "1404/01/20", false, "آقای محمدی")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("دفترچه چک‌های دریافتی و پرداختی", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add cheque */ },
                containerColor = PrimaryEmerald,
                contentColor = DarkBackground
            ) {
                Icon(Icons.Default.Add, contentDescription = "ثبت چک")
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (item.isPayable) LossRed.copy(alpha = 0.2f) else ProfitGreen.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = if (item.isPayable) "پرداختی (صادره)" else "دریافتی",
                                    color = if (item.isPayable) LossRed else ProfitGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(item.dueDate, color = TextMuted, fontSize = 12.sp)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.partyName, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Text("${formatAmount(item.amount)} ریال", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }

                        Text("${item.bankName} • شماره صیاد/سریال: ${item.chequeNumber}", color = TextMuted, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
