package ir.marheil.crystal.feature.goals

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

data class GoalMock(
    val id: Long,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadline: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier
) {
    val goals = remember {
        listOf(
            GoalMock(1, "خرید خودرو", 800000000.0, 520000000.0, "1404/06/31"),
            GoalMock(2, "صندوق اضطراری", 200000000.0, 180000000.0, "1404/02/15"),
            GoalMock(3, "سفر تفریحی", 100000000.0, 45000000.0, "1404/05/01")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اهداف مالی و پس‌انداز", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add goal */ },
                containerColor = PrimaryEmerald,
                contentColor = DarkBackground
            ) {
                Icon(Icons.Default.Add, contentDescription = "افزودن هدف")
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
            items(goals) { goal ->
                val progress = (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)
                val percentage = (progress * 100).toInt()

                GlassCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(goal.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("$percentage%", color = PrimaryEmerald, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = PrimaryEmerald,
                            trackColor = DarkCardBorder
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "جمع‌آوری شده: ${formatAmount(goal.currentAmount)}",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                            Text(
                                "هدف: ${formatAmount(goal.targetAmount)}",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }

                        Text("مهلت: ${goal.deadline}", color = TextMuted, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
