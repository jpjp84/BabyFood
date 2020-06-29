package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import javax.inject.Inject


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject
    lateinit var scrollDispatcher: HomePagerScrollDispatcher

    private var adapter: CalendarAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarPager()
        setNavigation()

        viewModel.updateMonth()
    }

    private fun setCalendarPager() {
        if (adapter != null) {
            return
        }

        viewBinding.homeCalendarPager.adapter = CalendarAdapter(viewModel)
    }

    private fun setNavigation() {
//        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
//            openCalendarDetail(it)
//        })
//
//        viewModel.yearMonths.observe(viewLifecycleOwner, Observer {
//            (viewBinding.homeCalendarPager.adapter as HomeCalendarPageAdapter).submitList(it)
//        })
//
//        viewModel.insertedNewPage.observe(viewLifecycleOwner, EventObserver {
//            (viewBinding.homeCalendarPager.adapter as HomeCalendarPageAdapter).notifyItemInserted(it)
//        })
//
//        viewModel.onUpdateSavedDays.observe(viewLifecycleOwner, EventObserver {
//            val viewHolder =
//                viewBinding.homeCalendarPager.findViewHolderForAdapterPosition(it.first)
//
//            if (viewHolder is HomeCalendarPageAdapter.CalendarPageViewHolder) {
//                viewHolder.getChildAdapter().submitList(it.second)
//            }
//        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = HomeFragmentDirections.actionHomeFragmentToDayListFragment(item)
        findNavController().navigate(action)
    }
}
