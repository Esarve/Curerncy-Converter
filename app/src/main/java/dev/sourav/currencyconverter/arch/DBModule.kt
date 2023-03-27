package dev.sourav.currencyconverter.arch

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sourav.currencyconverter.dao.ConversionRateDao
import dev.sourav.currencyconverter.dao.CurrencyDao
import dev.sourav.currencyconverter.repo.CurrencyConvertRepo
import dev.sourav.currencyconverter.repo.CurrencyConvertRepoImpl
import javax.inject.Singleton

/**
 * Created by Sourav
 * On 3/22/2023 12:56 AM
 * For Currency Converter
 */

@Module
@InstallIn(SingletonComponent::class)
object DBModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDB {
        return Room.databaseBuilder(
            appContext,
            AppDB::class.java,
            "currency_converter"
        ).build()
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDB): CurrencyDao {
        return appDatabase.currencyDao()
    }

    @Provides
    fun providesConversionRateDao(appDatabase: AppDB):ConversionRateDao{
        return appDatabase.conversionRateDao()
    }

    @Provides
    fun providesCurrencyConverterRepo(currencyDao: CurrencyDao, conversionRateDao: ConversionRateDao, apiService: ApiService): CurrencyConvertRepo {
        return CurrencyConvertRepoImpl(conversionRateDao, currencyDao, apiService)
    }
}
