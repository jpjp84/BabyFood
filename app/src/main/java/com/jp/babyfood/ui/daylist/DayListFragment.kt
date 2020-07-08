package com.jp.babyfood.ui.daylist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.databinding.FragmentDaylistBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.EventObserver

class DayListFragment : BaseFragment<DayListViewModel, FragmentDaylistBinding>() {

    override fun getViewModelClass(): Class<DayListViewModel> = DayListViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_daylist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
//        setDayListAdapter()
//        setAddBtn()

//        viewModel.initDayLists(args.day)
    }

    private fun setDayListAdapter() {
//        viewBinding.dayListView.adapter = DayListAdapter(viewModel)
    }

    private fun setNavigation() {
        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
            openCalendarDetail(it)
        })
    }

    private fun setAddBtn() {
//        viewBinding.addDayBtn.postDelayed({ viewBinding.addDayBtn.show() }, 250)
    }

    private fun openCalendarDetail(item: Food?) {
        setFragmentResult("requestKey", bundleOf("bundleKey" to "result"))
    }
}