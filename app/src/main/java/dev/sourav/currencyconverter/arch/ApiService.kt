package dev.sourav.currencyconverter.arch

import dev.sourav.currencyconverter.others.ApiResponse
import dev.sourav.currencyconverter.models.ExchangeRateDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Sourav
 * On 3/22/2023 12:40 AM
 * For Currency Converter
 */
interface ApiService {
    @GET("api/latest.json")
    suspend fun getLatest(): Response<ExchangeRateDto>

    @GET("api/currencies.json")
    suspend fun getCurrencyList(): Response<Map<String, String>>
}