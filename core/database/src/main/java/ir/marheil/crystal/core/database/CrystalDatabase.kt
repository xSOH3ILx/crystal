package ir.marheil.crystal.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.marheil.crystal.core.database.dao.AlertDao
import ir.marheil.crystal.core.database.dao.AssetDao
import ir.marheil.crystal.core.database.dao.ChequeAndLiabilityDao
import ir.marheil.crystal.core.database.dao.CashFlowDao
import ir.marheil.crystal.core.database.dao.GoalDao
import ir.marheil.crystal.core.database.dao.TransactionDao
import ir.marheil.crystal.core.database.entity.AssetEntity
import ir.marheil.crystal.core.database.entity.CategoryEntity
import ir.marheil.crystal.core.database.entity.CashFlowCategoryEntity
import ir.marheil.crystal.core.database.entity.CashFlowEntity
import ir.marheil.crystal.core.database.entity.ChequeEntity
import ir.marheil.crystal.core.database.entity.FinancialGoalEntity
import ir.marheil.crystal.core.database.entity.LiabilityEntity
import ir.marheil.crystal.core.database.entity.NotificationAlertEntity

@Database(
    entities = [
        CategoryEntity::class,
        AssetEntity::class,
        TransactionEntity::class,
        CashFlowCategoryEntity::class,
        CashFlowEntity::class,
        FinancialGoalEntity::class,
        ChequeEntity::class,
        LiabilityEntity::class,
        NotificationAlertEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CrystalDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun transactionDao(): TransactionDao
    abstract fun cashFlowDao(): CashFlowDao
    abstract fun goalDao(): GoalDao
    abstract fun chequeAndLiabilityDao(): ChequeAndLiabilityDao
    abstract fun alertDao(): AlertDao
}
