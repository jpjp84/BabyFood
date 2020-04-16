package com.jp.babyfood.ui.weekcalendar

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentWeekCalendarBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.SnapToBlock


class WeekCalendarFragment : BaseFragment<WeekCalendarViewModel, FragmentWeekCalendarBinding>() {

    override fun getViewModelClass(): Class<WeekCalendarViewModel> =
        WeekCalendarViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_week_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.weekendView.adapter = WeekCalendarAdapter(viewModel)

        val snapToBlock = SnapToBlock(7)
        snapToBlock.attachToRecyclerView(viewBinding.weekendView)
        viewModel.createCalendar("4")

        viewBinding.weekendView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var scrolledPosition = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrolledPosition =
                    snapToBlock.findTargetSnapPosition(recyclerView.layoutManager!!, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrolledPosition == 7) {
                        viewModel.updateCalendar(true)
                        (viewBinding.weekendView.adapter as WeekCalendarAdapter).notifyItemRangeInserted(
                            0,
                            7
                        )

                    }
                    if (scrolledPosition == 14) {
                        viewModel.updateCalendar(false)
                        (viewBinding.weekendView.adapter as WeekCalendarAdapter).notifyItemRangeInserted(
                            21,
                            7
                        )
                    }
                }
            }
        }).apply {
            viewBinding.weekendView.scrollToPosition(7)
        }

        viewModel.days.observe(viewLifecycleOwner, Observer { t ->

        })

    }

    private fun openCalendarDetail(item: Day) {
        val action =
            WeekCalendarFragmentDirections.actionWeekCalendarFragmentToCalendarDetailFragment(item)
        findNavController().navigate(action)
    }
}