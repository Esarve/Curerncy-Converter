package dev.sourav.currencyconverter.models

/**
 * Created by Sourav
 * On 3/25/2023 8:59 PM
 * For Currency Converter
 */
data class ConversionRateWithCurrency(
    val currencyCode: String,
    val rate: Double,
    val name: String
)