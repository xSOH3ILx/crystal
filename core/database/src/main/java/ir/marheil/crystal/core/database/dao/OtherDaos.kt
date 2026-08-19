package ir.marheil.crystal.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.marheil.crystal.core.database.entity.CashFlowCategoryEntity
import ir.marheil.crystal.core.database.entity.CashFlowEntity
import ir.marheil.crystal.core.database.entity.ChequeEntity
import ir.marheil.crystal.core.database.entity.FinancialGoalEntity
import ir.marheil.crystal.core.database.entity.LiabilityEntity
import ir.marheil.crystal.core.database.entity.NotificationAlertEntity
import kotlinx.coroutines.flow.Flow

data class CashFlowWithCategoryTuple(
    val id: Long,
    val categoryId: Long,
    val type: String,
    val amountToman: Double,
    val dateShamsi: String,
    val description: String,
    val categoryName: String,
    val icon: String,
    val color: String
)

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM cash_flow_categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CashFlowCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CashFlowCategoryEntity): Long

    @Query("""
        SELECT cf.id, cf.category_id as categoryId, cf.type, cf.amount_toman as amountToman,
               cf.date_shamsi as dateShamsi, cf.description, cfc.name as categoryName,
               cfc.icon, cfc.color
        FROM cash_flows cf
        JOIN cash_flow_categories cfc ON cf.category_id = cfc.id
        ORDER BY cf.date_shamsi DESC, cf.id DESC
    """)
    fun getAllCashFlows(): Flow<List<CashFlowWithCategoryTuple>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashFlow(cashFlow: CashFlowEntity): Long

    @Delete
    suspend fun deleteCashFlow(cashFlow: CashFlowEntity)
}

@Dao
interface GoalDao {
    @Query("SELECT * FROM financial_goals ORDER BY target_date_shamsi ASC")
    fun getAllGoals(): Flow<List<FinancialGoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: FinancialGoalEntity): Long

    @Update
    suspend fun updateGoal(goal: FinancialGoalEntity)

    @Delete
    suspend fun deleteGoal(goal: FinancialGoalEntity)
}

@Dao
interface ChequeAndLiabilityDao {
    @Query("SELECT * FROM cheques ORDER BY due_date_shamsi ASC")
    fun getAllCheques(): Flow<List<ChequeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheque(cheque: ChequeEntity): Long

    @Update
    suspend fun updateCheque(cheque: ChequeEntity)

    @Delete
    suspend fun deleteCheque(cheque: ChequeEntity)

    @Query("SELECT * FROM liabilities ORDER BY due_date_shamsi ASC")
    fun getAllLiabilities(): Flow<List<LiabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiability(liability: LiabilityEntity): Long

    @Update
    suspend fun updateLiability(liability: LiabilityEntity)

    @Delete
    suspend fun deleteLiability(liability: LiabilityEntity)
}

@Dao
interface AlertDao {
    @Query("SELECT * FROM notification_alerts ORDER BY date_shamsi DESC, id DESC")
    fun getAllAlerts(): Flow<List<NotificationAlertEntity>>

    @Query("SELECT * FROM notification_alerts WHERE is_read = 0 ORDER BY date_shamsi DESC, id DESC")
    fun getUnreadAlerts(): Flow<List<NotificationAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: NotificationAlertEntity): Long

    @Query("UPDATE notification_alerts SET is_read = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE notification_alerts SET is_read = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notification_alerts WHERE id = :id")
    suspend fun deleteAlert(id: Long)
}
