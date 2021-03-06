package com.jp.babyfood.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jp.babyfood.BR
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseDialogFragment<VM : ViewModel, VB : ViewDataBinding> : DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun getViewModelClass(): Class<VM>

    protected val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
    }

    @LayoutRes
    abstract fun getViewLayoutRes(): Int

    protected lateinit var viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!::viewBinding.isInitialized) {
            viewBinding = DataBindingUtil.inflate(inflater, getViewLayoutRes(), container, false)
            viewBinding.apply {
                this.setVariable(BR.viewModel, viewModel)
                viewBinding.lifecycleOwner = this@BaseDialogFragment
                this.executePendingBindings()
            }
        }

        return viewBinding.root
    }
}