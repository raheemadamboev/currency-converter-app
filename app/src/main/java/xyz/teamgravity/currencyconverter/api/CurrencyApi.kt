package xyz.teamgravity.currencyconverter.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.teamgravity.currencyconverter.model.CurrencyModel

interface CurrencyApi {

    @GET("/latest")
    suspend fun getRates(@Query("base") base: String): Response<CurrencyModel>
}