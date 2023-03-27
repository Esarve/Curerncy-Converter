package dev.sourav.currencyconverter.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sourav.currencyconverter.arch.AppDB
import dev.sourav.currencyconverter.entity.ConversionRate
import dev.sourav.currencyconverter.entity.Currency
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sourav
 * On 3/26/2023 3:38 PM
 * For Currency Converter
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class ConversionRateDaoTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var conversionRateDao: ConversionRateDao
    private lateinit var db: AppDB
    private val conversionRates = listOf(
        ConversionRate("USD", 1.0, 100),
        ConversionRate("EUR", 0.85, 200),
        ConversionRate("GBP", 109.98, 300)
    )
    private val currencies = listOf(
        Currency("USD", "US Dollar"),
        Currency("EUR", "Euro"),
        Currency("GBP", "British Pound")
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDB::class.java).build()
        conversionRateDao = db.conversionRateDao()
        currencyDao = db.currencyDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertConversionRateAndGetByCurrencyCode() = runBlocking {
        val conversionRate = ConversionRate("USD", 1.0, 123456)
        conversionRateDao.insertConversionRate(conversionRate)

        val retrievedConversionRate = conversionRateDao.getConversionRate("USD")
        assertThat(retrievedConversionRate).isEqualTo(conversionRate)
    }

    @Test
    fun testInsertConversionRateWithSameCurrencyCodeReplacesExisting() = runBlocking {
        val existingConversionRate = ConversionRate("USD", 1.0, 12121212)
        conversionRateDao.insertConversionRate(existingConversionRate)

        val newConversionRate = ConversionRate("USD", 2.0, 12121212)
        conversionRateDao.insertConversionRate(newConversionRate)

        val retrievedConversionRate = conversionRateDao.getConversionRate("USD")
        assertThat(retrievedConversionRate).isEqualTo(newConversionRate)
    }

    @Test
    fun testInsertAllAndGetAll(): Unit = runBlocking {
        conversionRateDao.insertAllExchangeRate(conversionRates)

        val retrievedConversionRates = conversionRateDao.getAllRates()

        assertThat(retrievedConversionRates).containsExactlyElementsIn(conversionRates)
    }

    @Test
    fun testGetLastUpdatedTimeWithNoDataReturnsZero() {
        val lastUpdatedTime = conversionRateDao.getLastUpdatedTime()
        assertThat(lastUpdatedTime).isEqualTo(0)
    }

    @Test
    fun testGetLastUpdatedTimeReturnsMaxLastUpdatedTime() = runBlocking {
        conversionRateDao.insertAllExchangeRate(conversionRates)

        val lastUpdatedTime = conversionRateDao.getLastUpdatedTime()

        assertThat(lastUpdatedTime).isEqualTo(300L)
    }

    @Test
    fun testGetConversionRateWithCurrency() = runBlocking {

        currencyDao.insertCurrencies(currencies)
        conversionRateDao.insertAllExchangeRate(conversionRates)

        val result = conversionRateDao.getConversionRateWithCurrency()

        assertThat(result).hasSize(conversionRates.size)
        for (i in conversionRates.indices) {
            assertThat(result[i].currencyCode).isEqualTo(conversionRates[i].currencyCode)
            assertThat(result[i].rate).isEqualTo(conversionRates[i].rate)
            assertThat(result[i].name).isEqualTo(currencies[i].name)
        }
    }

    fun testGetConversionRateWithCurrencyWhenCurrencyIsEmpty() = runBlocking {
        conversionRateDao.insertAllExchangeRate(conversionRates)

        val result = conversionRateDao.getConversionRateWithCurrency()

        assertThat(result).isEmpty()
    }

    @Test
    fun testGetConversionRateWithCurrencyWithEmptyDatabase(): Unit = runBlocking {
        val result = conversionRateDao.getConversionRateWithCurrency()

        assertThat(result).isEmpty()
    }


}