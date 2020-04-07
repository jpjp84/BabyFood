package com.jp.babyfood.ui.calendarpage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentCalendarPageBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.ui.home.HomeFragmentDirections
import com.jp.babyfood.util.view.OnItemClickListener


class CalendarPageFragment : BaseFragment<CalendarPageViewModel, FragmentCalendarPageBinding>(),
    OnItemClickListener<Day> {

    override fun getViewModelClass(): Class<CalendarPageViewModel> =
        CalendarPageViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_page

    companion object {
        fun newInstance(month: String): CalendarPageFragment {
            val bundle = Bundle().apply { this.putString("MONTH", month) }

            val fragment = CalendarPageFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarAdapter()
        viewModel.updateCalendar(requireArguments().getString("MONTH"))
    }

    private fun setCalendarAdapter() {
        viewBinding.calendarPageView.adapter =
            CalendarAdapter(viewModel, this)
    }

    override fun onItemClick(item: Day) {
        val action = HomeFragmentDirections.actionCalendarPageFragmentToCalendarDetailFragment()
        findNavController().navigate(action)
    }
}