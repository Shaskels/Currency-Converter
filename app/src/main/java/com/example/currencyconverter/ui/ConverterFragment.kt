package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.ConverterMode
import com.example.currencyconverter.presentation.CurrencyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [ConverterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConverterFragment : Fragment() {

    private val viewModel: CurrencyViewModel by viewModels<CurrencyViewModel> { (requireActivity().applicationContext as CurrencyConverterApplication).appComponent.viewModelsFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as CurrencyConverterApplication).appComponent.injectConverterFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val upperTextInput: TextInputLayout = view.findViewById(R.id.enterCountUp)
        val downTextInput: TextInputLayout = view.findViewById(R.id.enterCountDown)
        val button: FloatingActionButton = view.findViewById(R.id.floatingActionButton)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedItem.collect { item ->
                    if (viewModel.mode == ConverterMode.TO_RUBLE) {
                        upperTextInput.helperText = item?.name
                    } else {
                        downTextInput.helperText = item?.name
                    }
                    val text = upperTextInput.editText?.text.toString()
                    onUpperTextChanged(text, downTextInput, viewModel.mode)
                }
            }
        }
        upperTextInput.editText?.doOnTextChanged { text, _, _, _ ->
            onUpperTextChanged(text.toString(), downTextInput, viewModel.mode)
        }

        button.setOnClickListener {
            when (viewModel.mode) {
                ConverterMode.TO_RUBLE -> viewModel.mode = ConverterMode.FROM_RUBLE
                ConverterMode.FROM_RUBLE -> viewModel.mode = ConverterMode.TO_RUBLE
            }
            val t = upperTextInput.helperText
            upperTextInput.helperText = downTextInput.helperText
            downTextInput.helperText = t
            upperTextInput.editText?.text = downTextInput.editText?.text
        }
    }

    private fun onUpperTextChanged(
        text: String, textInputLayout: TextInputLayout, mode: ConverterMode
    ) {
        if (text.isNotEmpty() && text.toBigDecimalOrNull() != null) {
            val result = if (mode == ConverterMode.TO_RUBLE) {
                viewModel.convertToRubles(
                    text.toBigDecimal()
                )
            } else {
                viewModel.convertToCurrency(
                    text.toBigDecimal()
                )
            }
            textInputLayout.editText?.setText("%.4f".format(Locale.ROOT, result))
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ConverterFragment()
    }
}