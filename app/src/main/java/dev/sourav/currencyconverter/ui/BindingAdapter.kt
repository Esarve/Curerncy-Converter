package dev.sourav.currencyconverter.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.sourav.currencyconverter.models.ConvertedValue

/**
 * Created by Sourav
 * On 3/25/2023 8:25 PM
 * For Currency Converter
 */

@BindingAdapter("amountString")
fun TextView.setAmountString(value: ConvertedValue) {
    text = String.format("%.2f %s", value.amount, value.currencyName )
}
