package dev.sourav.currencyconverter.ui

import com.google.common.truth.Truth.assertThat
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import dev.sourav.currencyconverter.models.ConvertedValue
import dev.sourav.currencyconverter.repo.CurrencyConvertRepo
import dev.sourav.currencyconverter.repo.MockRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Created by Sourav
 * On 3/26/2023 8:47 PM
 * For Currency Converter
 */
class MainActivityVMTest {

    private lateinit var viewModel:MainActivityVM
    private lateinit var repo : CurrencyConvertRepo
    private val exchangeRateList = listOf(
        ConversionRate("BDT",  0.8),
        ConversionRate("AUD", 0.7),
        ConversionRate("CNY", 11.0)
    )

    private val currencyList = listOf(
        Currency("BDT", "Bangladeshi TAKA"),
        Currency("AUD", "Australian Dollar"),
        Currency("CNY", "Chinese Yun")
    )

    @Before
    fun setup(){
        repo = MockRepo()
        viewModel = MainActivityVM(repo)
    }

    @Test
    fun `when conversion rate is empty, should return empty list`() = runBlocking {
        repo.insertAllExchangeRate(listOf())

        val expectedList = listOf<ConvertedValue>()
        val actualList = viewModel.getConvertedList(100.0, "USD")
        assertThat(expectedList).isEqualTo(actualList)
    }


    @Test
    fun `when conversion rate is not empty, should return converted list sorted by currency code`() = runBlocking {
        repo.insertAllExchangeRate(exchangeRateList)

        repo.insertAllCurrencies(currencyList)

        val res = viewModel.getConvertedList(100.0, "BDT")

        val expectedList = listOf(
            ConvertedValue((0.7* (100.0/0.8)), "AUD", "Australian Dollar"),
            ConvertedValue((11.0* (100.0/0.8)), "CNY", "Chinese Yun"),
            ConvertedValue((100.0/0.8), "USD", "United State Dollar")
        )
        assertThat(res).isEqualTo(expectedList.sortedBy { it.currencyCode })
    }

    @Test
    fun `when input amount is zero, should return empty list`() = runBlocking {
        repo.insertAllExchangeRate(exchangeRateList)

        repo.insertAllCurrencies(currencyList)

        val res = viewModel.getConvertedList(0.0, "BDT")

        assertThat(res).isEmpty()
    }

    @Test
    fun `when input amount is not zero but currency code is empty, should return empty list`() = runBlocking {
        repo.insertAllExchangeRate(exchangeRateList)

        repo.insertAllCurrencies(currencyList)

        val res = viewModel.getConvertedList(0.0, "")

        assertThat(res).isEmpty()
    }

    @Test
    fun `when currency list is empty, should return empty list`() = runBlocking {
        repo.insertAllExchangeRate(exchangeRateList)

        val res = viewModel.getConvertedList(0.0, "BDT")

        assertThat(res).isEmpty()
    }

    @Test
    fun `when getConversionRate is called, should return a list` () = runBlocking {
        viewModel.executeConversionRateFetch()

        val result = repo.getConversionRate("EUR")
        assertThat(result).isNotNull()
    }

    @Test
    fun `when lastupdated is less than 30 mins ago, should return a false` () = runBlocking {
        val res = viewModel.shouldFetchFromAPI(System.currentTimeMillis() - 21 * 60000)

        assertThat(res).isFalse()
    }

    @Test
    fun `when lastupdated is more than 30 mins ago, should return a true` () = runBlocking {
        val res = viewModel.shouldFetchFromAPI(System.currentTimeMillis() - 33 * 60000)

        assertThat(res).isTrue()
    }


}