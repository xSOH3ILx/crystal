package ir.marheil.crystal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.marheil.crystal.core.designsystem.theme.*
import ir.marheil.crystal.feature.assets.AssetsScreen
import ir.marheil.crystal.feature.cashflow.CashFlowScreen
import ir.marheil.crystal.feature.cheques.ChequesScreen
import ir.marheil.crystal.feature.dashboard.DashboardScreen
import ir.marheil.crystal.feature.goals.GoalsScreen
import ir.marheil.crystal.feature.liabilities.LiabilitiesScreen
import ir.marheil.crystal.feature.settings.SettingsScreen
import ir.marheil.crystal.feature.transactions.TransactionsScreen
import ir.marheil.crystal.navigation.AppHub
import ir.marheil.crystal.navigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = DarkColorScheme) {
                val navController = rememberNavController()
                var currentHub by remember { mutableStateOf(AppHub.INVESTMENT) }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        HubSelectorBar(
                            selectedHub = currentHub,
                            onHubSelected = { hub ->
                                currentHub = hub
                                val startScreen = Screen.getScreensForHub(hub).first()
                                navController.navigate(startScreen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val currentHubScreens = Screen.getScreensForHub(currentHub)
                        if (currentHubScreens.size > 1) {
                            NavigationBar(containerColor = DarkSurface) {
                                currentHubScreens.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                                        label = { Text(screen.title, fontSize = 12.sp) },
                                        selected = currentRoute == screen.route,
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = PrimaryEmerald,
                                            selectedTextColor = PrimaryEmerald,
                                            unselectedIconColor = TextSecondary,
                                            unselectedTextColor = TextSecondary,
                                            indicatorColor = DarkSurfaceVariant
                                        ),
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    containerColor = DarkBackground
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Dashboard.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Investment Hub
                        composable(Screen.Dashboard.route) { DashboardScreen() }
                        composable(Screen.Assets.route) { AssetsScreen() }
                        composable(Screen.Transactions.route) { TransactionsScreen() }

                        // Personal Life Hub
                        composable(Screen.CashFlow.route) { CashFlowScreen() }
                        composable(Screen.Goals.route) { GoalsScreen() }
                        composable(Screen.Liabilities.route) { LiabilitiesScreen() }
                        composable(Screen.Cheques.route) { ChequesScreen() }

                        // Settings
                        composable(Screen.Settings.route) { SettingsScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun HubSelectorBar(
    selectedHub: AppHub,
    onHubSelected: (AppHub) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = DarkSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkBackground)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AppHub.entries.forEach { hub ->
                    val isSelected = hub == selectedHub
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) PrimaryEmerald.copy(alpha = 0.2f) else Color.Transparent)
                            .clickable { onHubSelected(hub) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = hub.icon,
                                contentDescription = hub.title,
                                tint = if (isSelected) PrimaryEmerald else TextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = hub.title,
                                color = if (isSelected) PrimaryEmerald else TextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
