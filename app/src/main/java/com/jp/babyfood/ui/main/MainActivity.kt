package com.jp.babyfood.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jp.babyfood.R
import com.jp.babyfood.databinding.ActivityMainBinding
import com.jp.babyfood.ui.base.BaseActivity
import com.jp.babyfood.util.LogUtil

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val TAG = LogUtil.makeLogTag(MainActivity::class.java)

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
        viewBinding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.calendarDetailFragment) {
                viewBinding.bottomNavigation.visibility = View.GONE
                return@addOnDestinationChangedListener
            }

            viewBinding.bottomNavigation.visibility = View.VISIBLE
        }
    }
}
