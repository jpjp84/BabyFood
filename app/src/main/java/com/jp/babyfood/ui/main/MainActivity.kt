package com.jp.babyfood.ui.main

import android.os.Bundle
import com.jp.babyfood.R
import com.jp.babyfood.databinding.ActivityMainBinding
import com.jp.babyfood.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateUser()
    }
}
