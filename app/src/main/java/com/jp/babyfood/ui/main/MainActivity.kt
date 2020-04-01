package com.jp.babyfood.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jp.babyfood.R
import com.jp.babyfood.databinding.ActivityMainBinding
import com.jp.babyfood.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.mainToolbar)

        val navController: NavController = findNavController(R.id.nav_host_fragment)


        viewModel.updateUser()
    }
}
