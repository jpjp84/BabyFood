package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.view.View
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment


class CalendarDetailFragment : BaseFragment<CalendarDetailViewModel, FragmentHomeBinding>() {

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}