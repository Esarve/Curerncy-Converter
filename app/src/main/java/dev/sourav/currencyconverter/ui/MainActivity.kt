package dev.sourav.currencyconverter.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.sourav.currencyconverter.others.NetworkUtils
import dev.sourav.currencyconverter.R
import dev.sourav.currencyconverter.databinding.ActivityMainBinding
import dev.sourav.currencyconverter.entity.Currency

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainActivityVM by viewModels()
    private val recyclerViewAdapter : ConvertedValueAdapter by lazy {
        ConvertedValueAdapter(emptyList())
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initOnCreateView() {
        bindingView.viewmodel = viewModel
        bindingView.lifecycleOwner = this

        if (NetworkUtils.hasInternetConnection(applicationContext)){
            bindingView.offlineMode.visibility = GONE
        }else{
            bindingView.offlineMode.visibility = VISIBLE
        }

        setupInternetConnectionListener()
        initVMListeners()
        initConvertedView()


    }

    private fun setupInternetConnectionListener() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                viewModel.getCurrencyList()
                viewModel.getConversionRate()
                Log.d("##M", "onAvailable: ${Thread.currentThread().name}")
                Handler(Looper.getMainLooper()).post {
                    bindingView.offlineMode.visibility = GONE
                    showSnackbar("Data Fetch Successfully")
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                bindingView.offlineMode.visibility = VISIBLE

            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun initConvertedView() {
        with(bindingView.recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }
    }

    private fun initVMListeners() {
        with(viewModel) {
            getConversionRate()
            currencyListMLD.observe(this@MainActivity) {list ->
                list.getContentIfNotHandled()?.let {
                    bindingView.spinner.adapter = CurrencySpinnerAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        it
                    )
                }

            }

            errorMLD.observe(this@MainActivity){ msg ->
                msg.getContentIfNotHandled()?.let{
                    showSnackbar(it)
                }

            }

            amount.observe(this@MainActivity) { value ->
                if (value.isNullOrEmpty()) {
                    showRecyclerView(false)
                } else {
                    Log.d("MA", "initOnCreateView: $value")
                    bindingView.spinner.selectedItem?.let {
                        showRecyclerView(true)
                        viewModel.executeConversion(value.toDouble(), (bindingView.spinner.selectedItem as Currency).code)
                    }
                }
            }

            bindingView.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    try {
                        val res = parent?.getItemAtPosition(position) as Currency
                        if(bindingView.textInputEditText.text.toString().isNotEmpty()){
                            val amount = bindingView.textInputEditText.text.toString().toDouble()
                            showRecyclerView(true)
                            viewModel.executeConversion(amount, res.code)
                        }
                    }catch (e:  Exception){
                        e.printStackTrace()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showRecyclerView(false)
                }

            }
            getCurrencyList()

            convertedListMLD.observe(this@MainActivity) {
                if (it != null && it.isNotEmpty()) {
                    recyclerViewAdapter.setData(it)
                }
            }
        }


    }

    private fun showRecyclerView(state: Boolean){
        val recyclerViewState = if (state) VISIBLE else GONE
        val promptTextState = if (state) GONE else VISIBLE

        bindingView.recyclerView.visibility = recyclerViewState
        bindingView.promptTxt.visibility = promptTextState
    }

    private fun showSnackbar(msg: String){
        val snackbar = Snackbar.make(bindingView.parent, msg, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.RED) // Set red background
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE) // Set white text
        snackbar.show()
    }

}