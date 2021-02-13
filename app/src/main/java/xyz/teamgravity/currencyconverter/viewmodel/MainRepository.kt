package xyz.teamgravity.currencyconverter.viewmodel

import xyz.teamgravity.currencyconverter.api.Resource
import xyz.teamgravity.currencyconverter.model.CurrencyModel

interface MainRepository {

    suspend fun getRates(base: String): Resource<CurrencyModel>

}