package xyz.teamgravity.currencyconverter.viewmodel

import xyz.teamgravity.currencyconverter.api.CurrencyApi
import xyz.teamgravity.currencyconverter.api.Resource
import xyz.teamgravity.currencyconverter.model.CurrencyModel
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRates(base: String): Resource<CurrencyModel> =
        try {
            val response = api.getRates(base)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error occurred")
        }
}