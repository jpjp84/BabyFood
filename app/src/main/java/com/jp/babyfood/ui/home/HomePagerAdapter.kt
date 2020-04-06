package com.jp.babyfood.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jp.babyfood.data.entity.Month
import com.jp.babyfood.ui.calendarpage.CalendarPageFragment

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val months = mutableListOf<Month>()

    override fun getItemCount(): Int {
        return months.size
    }

    override fun createFragment(position: Int): Fragment {
        return CalendarPageFragment.newInstance()
    }

    fun setPage(newMonth: List<Month>) {
        this.months.addAll(newMonth)
    }
}