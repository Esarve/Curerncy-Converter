package dev.sourav.currencyconverter.arch

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sourav.currencyconverter.dao.ConversionRateDao
import dev.sourav.currencyconverter.dao.CurrencyDao
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency

/**
 * Created by Sourav
 * On 3/22/2023 12:55 AM
 * For Currency Converter
 */
@Database(entities = [Currency::class, ConversionRate::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun conversionRateDao(): ConversionRateDao
}
