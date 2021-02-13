package xyz.teamgravity.currencyconverter.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.teamgravity.currencyconverter.api.CurrencyApi
import xyz.teamgravity.currencyconverter.viewmodel.CurrencyRepository
import xyz.teamgravity.currencyconverter.viewmodel.MainRepository
import javax.inject.Singleton

private const val BASE_URL = "https://api.exchangeratesapi.io"

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CurrencyApi::class.java)

    @Provides
    fun provideMainRepository(api: CurrencyApi) = CurrencyRepository(api) as MainRepository
}