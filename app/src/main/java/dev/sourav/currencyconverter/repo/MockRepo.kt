package dev.sourav.currencyconverter.repo

import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import dev.sourav.currencyconverter.models.ConversionRateWithCurrency
import dev.sourav.currencyconverter.models.ExchangeRateDto
import dev.sourav.currencyconverter.others.ApiResponse

/**
 * Created by Sourav
 * On 3/26/2023 8:33 PM
 * For Currency Converter
 */
class MockRepo : CurrencyConvertRepo {

    private val currencies = mutableListOf<Currency>()
    private val conversionRates = mutableListOf<ConversionRate>()
    private var lastUpdatedTime: Long = 0

    override suspend fun getConversionRate(currencyCode: String): ConversionRate? {
        return conversionRates.find { it.currencyCode == currencyCode }
    }

    override suspend fun getLastUpdated(): Long {
        return lastUpdatedTime
    }

    override suspend fun getAllRatesWithName(): List<ConversionRateWithCurrency> {
        if(conversionRates.isEmpty() || currencies.isEmpty()){
            return emptyList()
        }
        return conversionRates.map { conversionRate ->
            val currency = currencies.find { it.code == conversionRate.currencyCode }
            ConversionRateWithCurrency(currency?.code ?: "",conversionRate.rate, currency!!.name)
        }
    }

    override suspend fun insertAllExchangeRate(conversionRates: List<ConversionRate>) {
        this.conversionRates.addAll(conversionRates)
        lastUpdatedTime = System.currentTimeMillis()
    }

    override suspend fun insertAllCurrencies(currencies: List<Currency>) {
        this.currencies.addAll(currencies)
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        return currencies
    }

    override suspend fun getCurrencyListAPI(): ApiResponse<Map<String, String>> {
        // return a fake response
        val currencyMap = mapOf("USD" to "US Dollar", "EUR" to "Euro", "GBP" to "British Pound")
        return ApiResponse.success(currencyMap)
    }

    override suspend fun getExchangeRate(): ApiResponse<ExchangeRateDto> {
        // return a fake response
        val exchangeRateDto = ExchangeRateDto(
            disclaimer = "askdlfjjklasdfjkl",
            license =  "klsdjfsdklfj",
            base = "USD",
            timestamp = 12312312312,
            rates = mapOf("EUR" to 0.8, "GBP" to 0.7)
        )
        return ApiResponse.success(exchangeRateDto)
    }
}
