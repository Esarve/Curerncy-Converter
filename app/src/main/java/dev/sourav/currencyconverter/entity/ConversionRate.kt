package dev.sourav.currencyconverter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sourav
 * On 3/25/2023 1:14 PM
 * For Currency Converter
 */
@Entity(tableName = "conversion_rates")
data class ConversionRate(
    @PrimaryKey
    val currencyCode: String,
    val rate: Double,
    val lastUpdatedTime: Long = System.currentTimeMillis()
)