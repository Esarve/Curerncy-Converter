package dev.sourav.currencyconverter

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Sourav
 * On 3/22/2023 1:09 AM
 * For Currency Converter
 */
class AppIdInterceptor(private val appId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Token $appId")
            .build()

        return chain.proceed(newRequest)
    }
}