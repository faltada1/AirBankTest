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
    abstract val viewModelBindingVariable: Int
    abstract val layoutId: Int

    lateinit var binding: B
    lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setLifecycleOwner(this)
        binding.setVariable(viewModelBindingVariable, viewModel)
        binding.executePendingBindings()
    }


    override fun onDestroy() {
        viewModel.detachView()
        super.onDestroy()
    }

}