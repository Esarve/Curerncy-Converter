package dev.sourav.currencyconverter.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import dev.sourav.currencyconverter.models.ConvertedValue
import dev.sourav.currencyconverter.models.ExchangeRateDto
import dev.sourav.currencyconverter.others.ApiResponse
import dev.sourav.currencyconverter.others.Event
import dev.sourav.currencyconverter.others.Status
import dev.sourav.currencyconverter.repo.CurrencyConvertRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Sourav
 * On 3/22/2023 12:46 AM
 * For Currency Converter
 */

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val repo: CurrencyConvertRepo
) : ViewModel() {

    private var _currencies = MutableLiveData<Event<List<ConversionRate>>>()
    val currencies: LiveData<Event<List<ConversionRate>>> = _currencies
    val currencyListMLD = MutableLiveData<Event<List<Currency>>>()
    private val _convertedList = MutableLiveData<List<ConvertedValue>>()
    val convertedListMLD = _convertedList

    val amount = MutableLiveData<String>()
    val currencyCode = MutableLiveData<String>()

    private val _errorMLD = MutableLiveData<Event<String>>()
    val errorMLD : LiveData<Event<String>> = _errorMLD



    fun executeConversion(amount: Double, selectedCode: String) {
        viewModelScope.launch {
            val convertedList = getConvertedList(amount, selectedCode)
            _convertedList.postValue(convertedList)
        }
    }

    suspend fun getConvertedList(amount: Double, selectedCode: String): List<ConvertedValue> {
        val conversionRate = repo.getAllRatesWithName()
        val currencyRate = repo.getConversionRate(selectedCode)
        val convertedList = mutableListOf<ConvertedValue>()
        withContext(Dispatchers.Default) {
            if (conversionRate.isNotEmpty() && amount > 0.0) {
                val toUsd = amount.div(currencyRate!!.rate)

                conversionRate.forEach { item ->
                    if (item.currencyCode != selectedCode)
                        convertedList.add(
                            ConvertedValue(
                                item.rate.times(toUsd),
                                item.currencyCode,
                                item.name
                            )
                        )
                }
                convertedList.add(
                    ConvertedValue(
                        toUsd,
                        "USD",
                        "United State Dollar"
                    )
                )
            }
        }
        return convertedList.sortedBy { it.currencyCode }
    }

    fun getConversionRate() {
        viewModelScope.launch {
            executeConversionRateFetch()
        }
    }

    suspend fun executeConversionRateFetch() {
        val lasUpdated = repo.getLastUpdated()

        if (shouldFetchFromAPI(lasUpdated)) {
            val response = repo.getExchangeRate()
            when (response.status) {
                Status.SUCCESS -> setSuccess(response.data!!)
                Status.ERROR -> _errorMLD.postValue(Event(response.message?:"Some Error Occurred"))
                Status.LOADING -> {
                    // nothing right now
                }
            }
        }
    }

    fun shouldFetchFromAPI(lastUpdated: Long): Boolean{
        return lastUpdated + 30 * 60000 < System.currentTimeMillis()
    }

    fun getCurrencyList() {
        viewModelScope.launch {
            val listOfCurrencies = repo.getAllCurrencies()

            if (listOfCurrencies.isEmpty()) {
                val response = repo.getCurrencyListAPI()
                when (response.status) {
                    Status.SUCCESS -> {
                        val result = response.data;
                        val currencies: MutableList<Currency> = mutableListOf()

                        for ((code, name) in result!!) {
                            val currency = Currency(code, name)
                            currencies.add(currency)
                        }
                        currencyListMLD.postValue(Event(currencies.sortedBy { it.name }))
                        repo.insertAllCurrencies(currencies)
                    }
                    Status.ERROR -> _errorMLD.postValue(Event(response.message?:"Some Error Occurred"))
                    Status.LOADING -> {
                        //Nothing right now
                    }
                }
            } else {
                currencyListMLD.postValue(Event(listOfCurrencies.sortedBy { it.name }))
            }
        }
    }

    private suspend fun setSuccess(exchangeRateDto: ExchangeRateDto) {
        val data = exchangeRateDto.rates
        repo.insertAllExchangeRate(data.map {
            ConversionRate(
                it.key,
                it.value,
                System.currentTimeMillis()
            )
        })
    }
}
