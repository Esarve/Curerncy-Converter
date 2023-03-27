package dev.sourav.currencyconverter.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sourav.currencyconverter.arch.AppDB
import dev.sourav.currencyconverter.entity.Currency
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sourav
 * On 3/26/2023 3:59 PM
 * For Currency Converter
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyDaoTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: AppDB
    private  val currencies = listOf(
        Currency("USD", "US Dollar"),
        Currency("EUR", "Euro"),
        Currency("GBP", "British Pound")
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDB::class.java).build()
        currencyDao = db.currencyDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertCurrencies(): Unit = runBlocking {
        currencyDao.insertCurrencies(currencies)

        val result = currencyDao.getAllCurrencies()
        assertThat(result).hasSize(currencies.size)
        assertThat(result).containsExactlyElementsIn(currencies)
    }

    @Test
    fun testGetAllCurrencies(): Unit = runBlocking {
        currencyDao.insertCurrencies(currencies)

        val result = currencyDao.getAllCurrencies()

        assertThat(result).hasSize(currencies.size)
        assertThat(result).containsExactlyElementsIn(currencies)
    }

    @Test
    fun testGetAllCurrenciesWithEmptyDatabase() = runBlocking {
        val result = currencyDao.getAllCurrencies()

        assertThat(result).isEmpty()
    }

}