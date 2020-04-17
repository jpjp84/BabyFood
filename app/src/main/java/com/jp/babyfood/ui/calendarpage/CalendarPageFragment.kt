package com.jp.babyfood.ui.calendarpage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentCalendarPageBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.Constants.KEY_DAYS
import com.jp.babyfood.util.Constants.KEY_MONTH
import com.jp.babyfood.util.EventObserver


class CalendarPageFragment : BaseFragment<CalendarPageViewModel, FragmentCalendarPageBinding>() {

    override fun getViewModelClass(): Class<CalendarPageViewModel> =
        CalendarPageViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_page

    companion object {
        fun newInstance(month: String, days: List<Day>): CalendarPageFragment {
            val bundle = Bundle().apply {
                this.putString(KEY_MONTH, month)
                this.putParcelableArray(KEY_DAYS, days.toTypedArray())
            }

            val fragment = CalendarPageFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarAdapter()
        setNavigation()

        viewModel.updateCalendar(
//            requireArguments().getString(KEY_MONTH),
//            requireArguments().getParcelableArray(KEY_DAYS)?.map { it as Day }!!.toList()
        )
    }

    private fun setCalendarAdapter() {
        viewBinding.calendarPageView.adapter = CalendarAdapter(viewModel)
    }

    private fun setNavigation() {
        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
            openCalendarDetail(it)
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = CalendarPageFragmentDirections.actionCalendarPageFragmentToWeekCalendarFragment()
//        val action = HomeFragmentDirections.actionHomeFragmentToWeekCalendarFragment()
        findNavController().navigate(action)
    }
}