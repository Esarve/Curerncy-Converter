package dev.sourav.currencyconverter.others

/**
 * Created by Sourav
 * On 3/22/2023 1:12 AM
 * For Currency Converter
 */
sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val msg: String) : Response<Nothing>()
}