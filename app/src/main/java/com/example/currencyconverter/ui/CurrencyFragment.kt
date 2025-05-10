package com.example.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.CurrencyListAdapter
import com.example.currencyconverter.presentation.CurrencyListState
import com.example.currencyconverter.presentation.CurrencyViewModel
import com.example.currencyconverter.presentation.UpdateWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrencyFragment : Fragment() {

    private val viewModel: CurrencyViewModel by viewModels<CurrencyViewModel> { (requireActivity().applicationContext as CurrencyConverterApplication).appComponent.viewModelsFactory() }
    private lateinit var adapter: CurrencyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as CurrencyConverterApplication).appComponent.injectCurrencyFragment(
            this
        )

        adapter =
            CurrencyListAdapter(ArrayList()) { item, position ->
                viewModel.updateClickedItem(
                    item,
                    position
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.currencyListState.observe(viewLifecycleOwner) { result ->
            renderState(result)
        }
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.currency_list)
        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateCurrenciesList()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.getCurrenciesList()
    }

    private fun renderState(state: CurrencyListState) {
        when (state) {
            is CurrencyListState.Error -> Toast.makeText(
                context,
                "Failed to fetch",
                Toast.LENGTH_SHORT
            ).show()

            is CurrencyListState.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT)
                .show()

            is CurrencyListState.Success -> adapter.setNewItems(state.currencyList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrencyFragment()
    }


}