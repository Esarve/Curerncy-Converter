package dev.sourav.currencyconverter.models

/**
 * Created by Sourav
 * On 3/24/2023 1:23 PM
 * For Currency Converter
 */
import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(
    @SerializedName("disclaimer")
    val disclaimer: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
