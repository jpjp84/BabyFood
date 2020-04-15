package com.jp.babyfood.ui.weekcalendar

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentWeekCalendarBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.LogUtil.LOGI
import com.jp.babyfood.util.SnapToBlock


class WeekCalendarFragment : BaseFragment<WeekCalendarViewModel, FragmentWeekCalendarBinding>() {

    override fun getViewModelClass(): Class<WeekCalendarViewModel> =
        WeekCalendarViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_week_calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.weekendView.adapter = WeekCalendarAdapter(viewModel)
//        PagerSnapHelper().attachToRecyclerView(viewBinding.weekendView)

        val snapToBlock = SnapToBlock(7)
        snapToBlock.attachToRecyclerView(viewBinding.weekendView)
        viewModel.updateCalendar("4")
//        viewBinding.weekendView.selec
        viewBinding.weekendView.scrollToPosition(16 - (16 % 7))
        viewBinding.weekendView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                LOGI("BF_TAG", "scrolled ! : ${snapToBlock.findTargetSnapPosition(recyclerView.layoutManager!!, dx, dy)}")
            }
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action =
            WeekCalendarFragmentDirections.actionWeekCalendarFragmentToCalendarDetailFragment(item)
        findNavController().navigate(action)
    }
}