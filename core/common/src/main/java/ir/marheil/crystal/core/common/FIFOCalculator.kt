package ir.marheil.crystal.core.common

import ir.marheil.crystal.core.model.Transaction
import ir.marheil.crystal.core.model.TransactionType

data class AssetFIFOValuation(
    val remainingQuantity: Double,
    val totalCostBasisToman: Double,
    val averageBuyPriceToman: Double,
    val realizedPnLToman: Double
)

object FIFOCalculator {
    private data class BuyLot(
        var remainingQty: Double,
        val unitPrice: Double
    )

    fun calculateAssetValuation(transactions: List<Transaction>): AssetFIFOValuation {
        val sortedTransactions = transactions.sortedWith(
            compareBy<Transaction> { it.dateShamsi }.thenBy { it.id }
        )

        val buyLots = mutableListOf<BuyLot>()
        var realizedPnL = 0.0

        for (tx in sortedTransactions) {
            when (tx.type) {
                TransactionType.BUY, TransactionType.DEPOSIT -> {
                    if (tx.amount > 0) {
                        buyLots.add(BuyLot(remainingQty = tx.amount, unitPrice = tx.priceToman))
                    }
                }
                TransactionType.SELL, TransactionType.WITHDRAW -> {
                    var qtyToSell = tx.amount
                    while (qtyToSell > 0 && buyLots.isNotEmpty()) {
                        val currentLot = buyLots.first()
                        if (currentLot.remainingQty <= qtyToSell) {
                            val soldQty = currentLot.remainingQty
                            realizedPnL += (tx.priceToman - currentLot.unitPrice) * soldQty - tx.feeToman
                            qtyToSell -= soldQty
                            buyLots.removeAt(0)
                        } else {
                            currentLot.remainingQty -= qtyToSell
                            realizedPnL += (tx.priceToman - currentLot.unitPrice) * qtyToSell - tx.feeToman
                            qtyToSell = 0.0
                        }
                    }
                }
            }
        }

        val remainingQty = buyLots.sumOf { it.remainingQty }
        val totalCostBasis = buyLots.sumOf { it.remainingQty * it.unitPrice }
        val avgBuyPrice = if (remainingQty > 0) totalCostBasis / remainingQty else 0.0

        return AssetFIFOValuation(
            remainingQuantity = remainingQty,
            totalCostBasisToman = totalCostBasis,
            averageBuyPriceToman = avgBuyPrice,
            realizedPnLToman = realizedPnL
        )
    }
}
