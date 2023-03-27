package dev.sourav.currencyconverter.repo

import dev.sourav.currencyconverter.others.ApiResponse
import dev.sourav.currencyconverter.arch.ApiService
import dev.sourav.currencyconverter.dao.ConversionRateDao
import dev.sourav.currencyconverter.dao.CurrencyDao
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import dev.sourav.currencyconverter.models.ConversionRateWithCurrency
import dev.sourav.currencyconverter.models.ExchangeRateDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Sourav
 * On 3/26/2023 6:23 PM
 * For Currency Converter
 */
class CurrencyConvertRepoImpl @Inject constructor(
    private val conversionRateDao: ConversionRateDao,
    private val currencyDao: CurrencyDao,
    private val apiService: ApiService
) : CurrencyConvertRepo {

    override suspend fun getConversionRate(currencyCode: String): ConversionRate? =
        withContext(Dispatchers.IO) {
            return@withContext conversionRateDao.getConversionRate(currencyCode)
        }

    override suspend fun getLastUpdated(): Long =
        withContext(Dispatchers.IO) {
            return@withContext conversionRateDao.getLastUpdatedTime()
        }

    override suspend fun getAllRatesWithName(): List<ConversionRateWithCurrency> =
        withContext(Dispatchers.IO) {
            return@withContext conversionRateDao.getConversionRateWithCurrency()
        }

    override suspend fun insertAllExchangeRate(conversionRates: List<ConversionRate>) =
        withContext(Dispatchers.IO) {
            conversionRateDao.insertAllExchangeRate(conversionRates)
        }

    override suspend fun insertAllCurrencies(currencies: List<Currency>) {
        withContext(Dispatchers.IO){
            currencyDao.insertCurrencies(currencies)
        }
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        return currencyDao.getAllCurrencies()
    }

    override suspend fun getCurrencyListAPI(): ApiResponse<Map<String, String>> {
        return try {
            val response = apiService.getCurrencyList()

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let ApiResponse.success(response.body())
                } ?: ApiResponse.error("An unknown error occurred", null)
            } else {
                ApiResponse.error("An unknown error occurred", null)
            }
        }catch (e: Exception){
            ApiResponse.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getExchangeRate(): ApiResponse<ExchangeRateDto> {
        return try {
            val response = apiService.getLatest()

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let ApiResponse.success(response.body())
                } ?: ApiResponse.error("An unknown error occurred", null)
            } else {
                ApiResponse.error("An unknown error occurred", null)
            }
        }catch (e: Exception){
            ApiResponse.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}