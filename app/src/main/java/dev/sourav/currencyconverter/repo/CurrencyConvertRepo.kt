package dev.sourav.currencyconverter.repo

import dev.sourav.currencyconverter.others.ApiResponse
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import dev.sourav.currencyconverter.models.ConversionRateWithCurrency
import dev.sourav.currencyconverter.models.ExchangeRateDto

/**
 * Created by Sourav
 * On 3/26/2023 6:22 PM
 * For Currency Converter
 */
interface CurrencyConvertRepo {
    suspend fun getConversionRate(currencyCode: String): ConversionRate?
    suspend fun getLastUpdated(): Long
    suspend fun getAllRatesWithName(): List<ConversionRateWithCurrency>
    suspend fun insertAllExchangeRate(conversionRates: List<ConversionRate>)
    suspend fun insertAllCurrencies(currencies: List<Currency>)
    suspend fun getAllCurrencies(): List<Currency>
    suspend fun getCurrencyListAPI(): ApiResponse<Map<String, String>>
    suspend fun getExchangeRate(): ApiResponse<ExchangeRateDto>
}