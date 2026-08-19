package ir.marheil.crystal.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Hub identifiers representing the primary workspaces of Crystal
 */
enum class AppHub(val title: String, val icon: ImageVector) {
    INVESTMENT("مدیریت سرمایه‌گذاری", Icons.Default.TrendingUp),
    PERSONAL_LIFE("مدیریت زندگی شخصی", Icons.Default.Person),
    SETTINGS("تنظیمات", Icons.Default.Settings)
}

/**
 * Sub-destinations under each Hub workspace
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector, val hub: AppHub) {
    // Investment Hub Screens
    data object Dashboard : Screen("invest_dashboard", "داشبورد ثروت", Icons.Default.Dashboard, AppHub.INVESTMENT)
    data object Assets : Screen("invest_assets", "دارایی‌ها", Icons.Default.AccountBalanceWallet, AppHub.INVESTMENT)
    data object Transactions : Screen("invest_transactions", "تراکنش‌ها", Icons.Default.ReceiptLong, AppHub.INVESTMENT)

    // Personal Life Hub Screens
    data object CashFlow : Screen("life_cashflow", "جریان نقدی", Icons.Default.AccountBalance, AppHub.PERSONAL_LIFE)
    data object Goals : Screen("life_goals", "اهداف مالی", Icons.Default.Flag, AppHub.PERSONAL_LIFE)
    data object Liabilities : Screen("life_liabilities", "وام و بدهی", Icons.Default.CreditCard, AppHub.PERSONAL_LIFE)
    data object Cheques : Screen("life_cheques", "چک‌ها", Icons.Default.FactCheck, AppHub.PERSONAL_LIFE)

    // Global Settings
    data object Settings : Screen("app_settings", "تنظیمات", Icons.Default.Settings, AppHub.SETTINGS)

    companion object {
        fun getScreensForHub(hub: AppHub): List<Screen> {
            return when (hub) {
                AppHub.INVESTMENT -> listOf(Dashboard, Assets, Transactions)
                AppHub.PERSONAL_LIFE -> listOf(CashFlow, Goals, Liabilities, Cheques)
                AppHub.SETTINGS -> listOf(Settings)
            }
        }
    }
}
