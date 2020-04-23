package com.jp.babyfood.ui.daylist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentDaylistBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.EventObserver

class DayListFragment : BaseFragment<DayListViewModel, FragmentDaylistBinding>() {

    private val args: DayListFragmentArgs by navArgs()

    override fun getViewModelClass(): Class<DayListViewModel> = DayListViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_daylist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
        setDayListAdapter()

        viewModel.initDayLists(args.day)
    }

    private fun setDayListAdapter() {
        viewBinding.dayListView.adapter = DayListAdapter(viewModel)
    }

    private fun setNavigation() {
        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
            openCalendarDetail(it)
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = DayListFragmentDirections.actionDayListFragmentToCalendarDetailFragment(item)
        findNavController().navigate(action)
    }
}