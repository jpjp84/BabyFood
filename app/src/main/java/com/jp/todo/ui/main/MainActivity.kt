package com.jp.todo.ui.main

import android.os.Bundle
import com.jp.todo.R
import com.jp.todo.databinding.ActivityMainBinding
import com.jp.todo.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateUser()
    }
}
