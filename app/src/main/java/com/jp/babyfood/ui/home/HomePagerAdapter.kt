package com.jp.babyfood.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jp.babyfood.ui.calendarpage.CalendarPageFragment

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val months = mutableListOf<String>()

    override fun getItemCount(): Int {
        return months.size
    }

    override fun createFragment(position: Int): Fragment {
        return CalendarPageFragment.newInstance(months[position])
    }

    fun setPage(newMonth: List<String>) {
        this.months.addAll(newMonth)
    }
}