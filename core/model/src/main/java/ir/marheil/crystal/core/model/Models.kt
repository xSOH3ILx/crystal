package ir.marheil.crystal.core.model

data class Category(
    val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class Asset(
    val id: Long = 0,
    val categoryId: Long,
    val name: String,
    val symbol: String,
    val unit: String = "",
    val autoUpdate: Boolean = false,
    val apiSource: String = "",
    val apiSymbol: String = "",
    val lastUpdatedAt: String = "",
    val categoryName: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class TransactionType(val value: String) {
    BUY("buy"),
    SELL("sell"),
    DEPOSIT("deposit"),
    WITHDRAW("withdraw")
}

data class Transaction(
    val id: Long = 0,
    val assetId: Long,
    val type: TransactionType,
    val amount: Double,
    val priceToman: Double,
    val feeToman: Double = 0.0,
    val dateShamsi: String,
    val notes: String = "",
    val relatedTransactionId: Long? = null,
    val assetName: String = "",
    val assetSymbol: String = "",
    val categoryName: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class CashFlowCategory(
    val id: Long = 0,
    val name: String,
    val type: String, // "income", "expense"
    val icon: String = "",
    val color: String = "#3B82F6",
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class CashFlow(
    val id: Long = 0,
    val categoryId: Long,
    val categoryName: String = "",
    val type: String, // "income", "expense"
    val amountToman: Double,
    val dateShamsi: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class FinancialGoal(
    val id: Long = 0,
    val title: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val targetDateShamsi: String,
    val category: String = "general",
    val assetId: Long? = null,
    val color: String = "#3B82F6",
    val notes: String = "",
    val status: String = "in_progress", // "in_progress", "completed", "cancelled"
    val progressPercent: Double = 0.0,
    val remainingAmount: Double = 0.0,
    val daysRemaining: Int = 0
)

data class Cheque(
    val id: Long = 0,
    val type: String, // "receivable", "payable"
    val amount: Double,
    val issueDateShamsi: String,
    val dueDateShamsi: String,
    val bankName: String,
    val chequeNumber: String,
    val sayadNumber: String = "",
    val receiverPayer: String,
    val status: String = "pending", // "pending", "passed", "bounced", "cancelled"
    val notes: String = ""
)

data class Liability(
    val id: Long = 0,
    val name: String,
    val totalAmount: Double,
    val remainingAmount: Double,
    val dueDateShamsi: String,
    val status: String = "active", // "active", "settled"
    val creditorDebtor: String = "",
    val type: String = "loan", // "loan", "debt", "receivable"
    val notes: String = ""
)

data class NotificationAlert(
    val id: Long = 0,
    val type: String,
    val title: String,
    val message: String,
    val severity: String = "info", // "info", "warning", "critical"
    val referenceId: String = "",
    val isRead: Boolean = false,
    val dateShamsi: String
)
