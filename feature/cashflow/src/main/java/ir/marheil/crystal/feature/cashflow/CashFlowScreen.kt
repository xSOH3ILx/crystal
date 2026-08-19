package ir.marheil.crystal.feature.cashflow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.marheil.crystal.core.common.formatAmount
import ir.marheil.crystal.core.designsystem.components.GlassCard
import ir.marheil.crystal.core.designsystem.theme.*

data class CashFlowItemMock(
    val id: Long,
    val title: String,
    val category: String,
    val amount: Double,
    val isExpense: Boolean,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashFlowScreen(
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            CashFlowItemMock(1, "حقوق ماهانه", "درآمد", 65000000.0, false, "1403/12/01"),
            CashFlowItemMock(2, "اجاره مسکن", "مسکن", 18000000.0, true, "1403/12/02"),
            CashFlowItemMock(3, "خرید سوپرمارکت", "خوراک", 4500000.0, true, "1403/12/05"),
            CashFlowItemMock(4, "سود سپرده بانکی", "سرمایه‌گذاری", 3200000.0, false, "1403/12/10")
        )
    }

    val totalIncome = items.filter { !it.isExpense }.sumOf { it.amount }
    val totalExpense = items.filter { it.isExpense }.sumOf { it.amount }
    val netCashFlow = totalIncome - totalExpense

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جریان نقدی (درآمد و هزینه)", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add income/expense dialog */ },
                containerColor = PrimaryEmerald,
                contentColor = DarkBackground
            ) {
                Icon(Icons.Default.Add, contentDescription = "افزودن جریان نقدی")
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
            item {
                GlassCard {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("خلاصه دخل و خرج ماهانه", color = TextSecondary, fontSize = 14.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("مجموع درآمد", color = TextMuted, fontSize = 12.sp)
                                Text(
                                    "${formatAmount(totalIncome)} ریال",
                                    color = ProfitGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("مجموع هزینه", color = TextMuted, fontSize = 12.sp)
                                Text(
                                    "${formatAmount(totalExpense)} ریال",
                                    color = LossRed,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                        Divider(color = DarkCardBorder)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("تراز خالص:", color = TextPrimary, fontWeight = FontWeight.Medium)
                            Text(
                                "${formatAmount(netCashFlow)} ریال",
                                color = if (netCashFlow >= 0) PrimaryEmerald else LossRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    "تراکنش‌های اخیر",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            items(items) { item ->
                GlassCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (item.isExpense) LossRed.copy(alpha = 0.15f) else ProfitGreen.copy(alpha = 0.15f),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (item.isExpense) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                        contentDescription = null,
                                        tint = if (item.isExpense) LossRed else ProfitGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Column {
                                Text(item.title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                Text("${item.category} • ${item.date}", color = TextMuted, fontSize = 12.sp)
                            }
                        }

                        Text(
                            text = "${if (item.isExpense) "-" else "+"}${formatAmount(item.amount)}",
                            color = if (item.isExpense) LossRed else ProfitGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
