package dev.sourav.currencyconverter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sourav
 * On 3/24/2023 1:49 PM
 * For Currency Converter
 */
@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey val code: String,
    val name: String
)