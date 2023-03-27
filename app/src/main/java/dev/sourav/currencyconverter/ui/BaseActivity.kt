package dev.sourav.currencyconverter.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by Sourav
 * On 3/24/2023 11:56 AM
 * For Currency Converter
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected val bindingView: VB
        get() = viewDataBinding
    private lateinit var viewDataBinding: VB

//    protected lateinit var navController: NavController
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this,getLayout())
        setContentView(viewDataBinding.root)

        initOnCreateView()
    }

    @LayoutRes
    abstract fun getLayout() :Int

    abstract fun initOnCreateView()
}