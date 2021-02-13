package xyz.teamgravity.currencyconverter.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import xyz.teamgravity.currencyconverter.databinding.ActivityMainBinding
import xyz.teamgravity.currencyconverter.viewmodel.CurrencyViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        event()
        button()
    }

    private fun event() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.conversion.collect { event ->
                    when (event) {
                        is CurrencyViewModel.CurrencyEvent.Success -> {
                            progressBar.visibility = View.INVISIBLE
                            resultT.setTextColor(Color.BLACK)
                            resultT.text = event.result
                        }

                        is CurrencyViewModel.CurrencyEvent.Empty -> {

                        }

                        is CurrencyViewModel.CurrencyEvent.Error -> {
                            progressBar.visibility = View.INVISIBLE
                            resultT.setTextColor(Color.RED)
                            resultT.text = event.error
                        }

                        is CurrencyViewModel.CurrencyEvent.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun button() {
        onConvert()
    }

    // convert button
    private fun onConvert() {
        binding.apply {
            convertB.setOnClickListener {
                viewModel.convert(amountField.text.toString().trim(), fromS.selectedItem.toString(), toS.selectedItem.toString())
            }
        }
    }
}