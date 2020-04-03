package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.LogUtil.makeLogTag
import com.jp.babyfood.util.view.OnItemClickListener


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    OnItemClickListener<Day> {
    private val TAG = makeLogTag(HomeFragment::class.java)

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarAdapter()
        viewModel.updateCalendar()
    }

    private fun setCalendarAdapter() {
        viewBinding.homeCalendarView.adapter = CalendarAdapter(viewModel, this)
    }

    override fun onItemClick(item: Day) {
        TODO("open Food Detail Fragment")
    }
}