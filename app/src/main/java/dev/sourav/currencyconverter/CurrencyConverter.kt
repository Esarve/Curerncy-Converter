package dev.sourav.currencyconverter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Sourav
 * On 3/24/2023 1:04 PM
 * For Currency Converter
 */
@HiltAndroidApp
class CurrencyConverter: Application() {
    companion object {
        lateinit var context: CurrencyConverter
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}