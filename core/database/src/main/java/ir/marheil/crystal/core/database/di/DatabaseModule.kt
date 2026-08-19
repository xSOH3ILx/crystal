package ir.marheil.crystal.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.marheil.crystal.core.database.CrystalDatabase
import ir.marheil.crystal.core.database.dao.AlertDao
import ir.marheil.crystal.core.database.dao.AssetDao
import ir.marheil.crystal.core.database.dao.CashFlowDao
import ir.marheil.crystal.core.database.dao.ChequeAndLiabilityDao
import ir.marheil.crystal.core.database.dao.GoalDao
import ir.marheil.crystal.core.database.dao.TransactionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCrystalDatabase(
        @ApplicationContext context: Context
    ): CrystalDatabase {
        return Room.databaseBuilder(
            context,
            CrystalDatabase::class.java,
            "crystal_database.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Seed standard default categories mirroring Go backend
                db.execSQL("INSERT INTO categories (name, created_at, updated_at) VALUES ('طلا و سکه', strftime('%s','now')*1000, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO categories (name, created_at, updated_at) VALUES ('ارز دیجیتال', strftime('%s','now')*1000, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO categories (name, created_at, updated_at) VALUES ('ارز فیات', strftime('%s','now')*1000, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO categories (name, created_at, updated_at) VALUES ('بورس', strftime('%s','now')*1000, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO categories (name, created_at, updated_at) VALUES ('نقدی و بانکی', strftime('%s','now')*1000, strftime('%s','now')*1000)")

                db.execSQL("INSERT INTO cash_flow_categories (name, type, icon, color, is_default, created_at) VALUES ('حقوق و درآمد', 'income', 'briefcase', '#10B981', 1, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO cash_flow_categories (name, type, icon, color, is_default, created_at) VALUES ('سود سرمایه‌گذاری', 'income', 'trending-up', '#10B981', 1, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO cash_flow_categories (name, type, icon, color, is_default, created_at) VALUES ('خوراک و پوشاک', 'expense', 'shopping-bag', '#EF4444', 1, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO cash_flow_categories (name, type, icon, color, is_default, created_at) VALUES ('اجاره و مسکن', 'expense', 'home', '#EF4444', 1, strftime('%s','now')*1000)")
                db.execSQL("INSERT INTO cash_flow_categories (name, type, icon, color, is_default, created_at) VALUES ('حمل و نقل', 'expense', 'truck', '#EF4444', 1, strftime('%s','now')*1000)")
            }
        }).build()
    }

    @Provides
    fun provideAssetDao(database: CrystalDatabase): AssetDao = database.assetDao()

    @Provides
    fun provideTransactionDao(database: CrystalDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun provideCashFlowDao(database: CrystalDatabase): CashFlowDao = database.cashFlowDao()

    @Provides
    fun provideGoalDao(database: CrystalDatabase): GoalDao = database.goalDao()

    @Provides
    fun provideChequeAndLiabilityDao(database: CrystalDatabase): ChequeAndLiabilityDao = database.chequeAndLiabilityDao()

    @Provides
    fun provideAlertDao(database: CrystalDatabase): AlertDao = database.alertDao()
}
