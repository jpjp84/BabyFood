package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarAdapter()
        viewModel.updateCalendar()
    }

    private fun setCalendarAdapter() {
        viewBinding.homeCalendarPager.offscreenPageLimit = 1
        viewBinding.homeCalendarPager.adapter =
            HomePagerAdapter(childFragmentManager, this@HomeFragment.lifecycle)
    }
}