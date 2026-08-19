package ir.marheil.crystal.core.common

import java.text.DecimalFormat

object Formatters {
    private val decimalFormat = DecimalFormat("#,###.##")

    fun formatMoney(amount: Double): String {
        return decimalFormat.format(amount)
    }

    fun formatQuantity(quantity: Double): String {
        return if (quantity % 1.0 == 0.0) {
            quantity.toLong().toString()
        } else {
            String.format("%.4f", quantity).trimEnd('0').trimEnd('.')
        }
    }
}

fun formatAmount(amount: Double): String = Formatters.formatMoney(amount)

