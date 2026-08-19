package ir.marheil.crystal.core.common

import ir.marheil.crystal.core.model.Transaction
import ir.marheil.crystal.core.model.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Test

class FIFOCalculatorTest {

    @Test
    fun calculateAssetValuation_singleBuy_correctCostBasis() {
        val transactions = listOf(
            Transaction(
                id = 1,
                assetId = 1,
                type = TransactionType.BUY,
                amount = 2.0,
                priceToman = 50000.0,
                dateShamsi = "1403/01/01"
            )
        )

        val valuation = FIFOCalculator.calculateAssetValuation(transactions)

        assertEquals(2.0, valuation.remainingQuantity, 0.0001)
        assertEquals(100000.0, valuation.totalCostBasisToman, 0.0001)
        assertEquals(50000.0, valuation.averageBuyPriceToman, 0.0001)
        assertEquals(0.0, valuation.realizedPnLToman, 0.0001)
    }

    @Test
    fun calculateAssetValuation_buyAndSell_correctRealizedPnL() {
        val transactions = listOf(
            Transaction(
                id = 1,
                assetId = 1,
                type = TransactionType.BUY,
                amount = 2.0,
                priceToman = 50000.0,
                dateShamsi = "1403/01/01"
            ),
            Transaction(
                id = 2,
                assetId = 1,
                type = TransactionType.SELL,
                amount = 1.0,
                priceToman = 60000.0,
                feeToman = 500.0,
                dateShamsi = "1403/01/05"
            )
        )

        val valuation = FIFOCalculator.calculateAssetValuation(transactions)

        assertEquals(1.0, valuation.remainingQuantity, 0.0001)
        assertEquals(50000.0, valuation.totalCostBasisToman, 0.0001)
        assertEquals(50000.0, valuation.averageBuyPriceToman, 0.0001)
        // Profit = (60000 - 50000) * 1 - 500 = 9500
        assertEquals(9500.0, valuation.realizedPnLToman, 0.0001)
    }
}
