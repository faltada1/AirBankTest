package cz.danfalta.airbank.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.danfalta.airbank.viewmodel.BaseViewModel

abstract class BaseActivity<B : ViewDataBinding, T : BaseViewModel> : AppCompatActivity(), LifecycleOwner {

    abstract val viewModelClass: Class<T>
    lateinit var binding: B
    val viewModel: T by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setLifecycleOwner(this)
    }

    /**
     * @return layout resource id
     */
    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        viewModel.detachView()
        super.onDestroy()
    }

}