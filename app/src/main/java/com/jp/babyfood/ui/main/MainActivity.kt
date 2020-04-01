package com.jp.babyfood.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jp.babyfood.R
import com.jp.babyfood.databinding.ActivityMainBinding
import com.jp.babyfood.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration.Builder(R.id.homeFragment, R.id.historyFragment)
            .setDrawerLayout(viewBinding.drawerLayout)
            .build()
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.mainToolbar)
        setNavigation()

        viewModel.updateUser()
    }

    private fun setNavigation() {
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        viewBinding.bottomNavigation.setupWithNavController(navController)
    }
}
