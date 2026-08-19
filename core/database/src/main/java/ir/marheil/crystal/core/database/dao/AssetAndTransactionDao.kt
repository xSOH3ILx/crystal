package ir.marheil.crystal.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.marheil.crystal.core.database.entity.AssetEntity
import ir.marheil.crystal.core.database.entity.CategoryEntity
import ir.marheil.crystal.core.database.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

data class AssetWithCategoryTuple(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val symbol: String,
    val unit: String,
    val autoUpdate: Boolean,
    val apiSource: String,
    val apiSymbol: String,
    val lastUpdatedAt: String,
    val categoryName: String
)

data class TransactionWithDetailsTuple(
    val id: Long,
    val assetId: Long,
    val type: String,
    val amount: Double,
    val priceToman: Double,
    val feeToman: Double,
    val dateShamsi: String,
    val notes: String,
    val relatedTransactionId: Long?,
    val assetName: String,
    val assetSymbol: String,
    val categoryName: String
)

@Dao
interface AssetDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("""
        SELECT a.id, a.category_id as categoryId, a.name, a.symbol, a.unit, 
               a.auto_update as autoUpdate, a.api_source as apiSource, a.api_symbol as apiSymbol, 
               a.last_updated_at as lastUpdatedAt, c.name as categoryName
        FROM assets a
        JOIN categories c ON a.category_id = c.id
        ORDER BY a.name ASC
    """)
    fun getAssetsWithCategory(): Flow<List<AssetWithCategoryTuple>>

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getAssetById(id: Long): AssetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity): Long

    @Update
    suspend fun updateAsset(asset: AssetEntity)

    @Delete
    suspend fun deleteAsset(asset: AssetEntity)

    @Query("SELECT COUNT(*) FROM transactions WHERE asset_id = :assetId")
    suspend fun getTransactionCountForAsset(assetId: Long): Int
}

@Dao
interface TransactionDao {
    @Query("""
        SELECT t.id, t.asset_id as assetId, t.type, t.amount, t.price_toman as priceToman,
               t.fee_toman as feeToman, t.date_shamsi as dateShamsi, t.notes, 
               t.related_transaction_id as relatedTransactionId,
               a.name as assetName, a.symbol as assetSymbol, c.name as categoryName
        FROM transactions t
        JOIN assets a ON t.asset_id = a.id
        JOIN categories c ON a.category_id = c.id
        ORDER BY t.date_shamsi DESC, t.id DESC
    """)
    fun getAllTransactions(): Flow<List<TransactionWithDetailsTuple>>

    @Query("SELECT * FROM transactions WHERE asset_id = :assetId ORDER BY date_shamsi ASC, id ASC")
    suspend fun getTransactionsForAsset(assetId: Long): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id OR related_transaction_id = :id")
    suspend fun deleteTransactionWithRelated(id: Long)
}
