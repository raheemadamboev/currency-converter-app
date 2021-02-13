package xyz.teamgravity.currencyconverter.model

data class CurrencyModel(
    val base: String,
    val date: String,
    val rates: RateModel
)