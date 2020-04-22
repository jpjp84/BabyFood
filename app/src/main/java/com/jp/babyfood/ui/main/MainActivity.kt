package com.jp.babyfood.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.jp.babyfood.R
import com.jp.babyfood.databinding.ActivityMainBinding
import com.jp.babyfood.ui.base.BaseActivity


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration.Builder(R.id.homeFragment, R.id.historyFragment)
            .setOpenableLayout(viewBinding.drawerLayout)
            .build()
    }

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.mainToolbar)
        setNavigation()

        viewModel.updateUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setNavigation() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
