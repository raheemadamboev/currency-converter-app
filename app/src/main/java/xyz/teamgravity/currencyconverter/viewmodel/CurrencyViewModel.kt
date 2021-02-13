package xyz.teamgravity.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.teamgravity.currencyconverter.api.Resource
import xyz.teamgravity.currencyconverter.model.RateModel
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val result: String) : CurrencyEvent()
        class Error(val error: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(amountStr: String, from: String, to: String) {

        val amount = amountStr.toFloatOrNull()

        if (amount == null) {
            _conversion.value = CurrencyEvent.Error("Not a valid amount")
            return
        }

        viewModelScope.launch {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRates(from)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Error(ratesResponse.message ?: "Error")
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(to, rates)

                    if (rate == null) {
                        _conversion.value = CurrencyEvent.Error("Error happened in conversion")
                    } else {
                        val convertedCurrency = round(amount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success("$amount $from = $convertedCurrency $to")
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: RateModel) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }
}