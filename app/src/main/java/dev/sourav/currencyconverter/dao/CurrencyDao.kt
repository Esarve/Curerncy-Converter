package dev.sourav.currencyconverter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sourav.currencyconverter.entity.Currency

/**
 * Created by Sourav
 * On 3/22/2023 12:55 AM
 * For Currency Converter
 */
@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Currency>)

    @Query("SELECT * FROM currencies")
    suspend fun getAllCurrencies(): List<Currency>
}