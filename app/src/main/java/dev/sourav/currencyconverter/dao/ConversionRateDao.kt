package dev.sourav.currencyconverter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.models.ConversionRateWithCurrency

/**
 * Created by Sourav
 * On 3/25/2023 1:15 PM
 * For Currency Converter
 */
@Dao
interface ConversionRateDao {

    @Query("SELECT * FROM conversion_rates WHERE currencyCode = :currencyCode")
    fun getConversionRate(currencyCode: String): ConversionRate?

    @Query("SELECT MAX(lastUpdatedTime) FROM conversion_rates")
    fun getLastUpdatedTime(): Long

    @Query("SELECT * FROM conversion_rates")
    fun getAllRatesLiveData():LiveData<List<ConversionRate>>

    @Query("SELECT * FROM conversion_rates")
    fun getAllRates():List<ConversionRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRate(conversionRate: ConversionRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExchangeRate(conversionRates: List<ConversionRate>)

    @Query("DELETE FROM conversion_rates")
    suspend fun deleteAll()

    @Query("SELECT conversion_rates.currencyCode, conversion_rates.rate, currencies.name FROM conversion_rates JOIN currencies ON conversion_rates.currencyCode = currencies.code")
    fun getConversionRateWithCurrency(): List<ConversionRateWithCurrency>
}
