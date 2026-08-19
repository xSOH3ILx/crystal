package ir.marheil.crystal.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index(value = ["name"], unique = true)]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "assets",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class AssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val name: String,
    val symbol: String,
    val unit: String = "",
    @ColumnInfo(name = "auto_update") val autoUpdate: Boolean = false,
    @ColumnInfo(name = "api_source") val apiSource: String = "",
    @ColumnInfo(name = "api_symbol") val apiSymbol: String = "",
    @ColumnInfo(name = "last_updated_at") val lastUpdatedAt: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = ["id"],
            childColumns = ["asset_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["asset_id"]), Index(value = ["date_shamsi"])]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "asset_id") val assetId: Long,
    val type: String, // "buy", "sell", "deposit", "withdraw"
    val amount: Double,
    @ColumnInfo(name = "price_toman") val priceToman: Double,
    @ColumnInfo(name = "fee_toman") val feeToman: Double = 0.0,
    @ColumnInfo(name = "date_shamsi") val dateShamsi: String,
    val notes: String = "",
    @ColumnInfo(name = "related_transaction_id") val relatedTransactionId: Long? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "cash_flow_categories",
    indices = [Index(value = ["name", "type"], unique = true)]
)
data class CashFlowCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String,
    val icon: String = "",
    val color: String = "#3B82F6",
    @ColumnInfo(name = "is_default") val isDefault: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "cash_flows",
    foreignKeys = [
        ForeignKey(
            entity = CashFlowCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["category_id"]), Index(value = ["date_shamsi"])]
)
data class CashFlowEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val type: String,
    @ColumnInfo(name = "amount_toman") val amountToman: Double,
    @ColumnInfo(name = "date_shamsi") val dateShamsi: String,
    val description: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "financial_goals",
    indices = [Index(value = ["target_date_shamsi"]), Index(value = ["status"])]
)
data class FinancialGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    @ColumnInfo(name = "target_amount") val targetAmount: Double,
    @ColumnInfo(name = "current_amount") val currentAmount: Double = 0.0,
    @ColumnInfo(name = "target_date_shamsi") val targetDateShamsi: String,
    val category: String = "general",
    @ColumnInfo(name = "asset_id") val assetId: Long? = null,
    val color: String = "#3B82F6",
    val notes: String = "",
    val status: String = "in_progress",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "cheques",
    indices = [Index(value = ["due_date_shamsi"]), Index(value = ["status"])]
)
data class ChequeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val amount: Double,
    @ColumnInfo(name = "issue_date_shamsi") val issueDateShamsi: String,
    @ColumnInfo(name = "due_date_shamsi") val dueDateShamsi: String,
    @ColumnInfo(name = "bank_name") val bankName: String,
    @ColumnInfo(name = "cheque_number") val chequeNumber: String,
    @ColumnInfo(name = "sayad_number") val sayadNumber: String = "",
    @ColumnInfo(name = "receiver_payer") val receiverPayer: String,
    val status: String = "pending",
    val notes: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "liabilities",
    indices = [Index(value = ["due_date_shamsi"]), Index(value = ["status"])]
)
data class LiabilityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "total_amount") val totalAmount: Double,
    @ColumnInfo(name = "remaining_amount") val remainingAmount: Double,
    @ColumnInfo(name = "due_date_shamsi") val dueDateShamsi: String,
    val status: String = "active",
    @ColumnInfo(name = "creditor_debtor") val creditorDebtor: String = "",
    val type: String = "loan",
    val notes: String = "",
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "notification_alerts",
    indices = [Index(value = ["type"]), Index(value = ["is_read"]), Index(value = ["date_shamsi"])]
)
data class NotificationAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val title: String,
    val message: String,
    val severity: String = "info",
    @ColumnInfo(name = "reference_id") val referenceId: String = "",
    @ColumnInfo(name = "is_read") val isRead: Boolean = false,
    @ColumnInfo(name = "date_shamsi") val dateShamsi: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
