package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.EventObserver
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import javax.inject.Inject


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject
    lateinit var scrollDispatcher: HomePagerScrollDispatcher

    private var adapter: HomeCalendarPageAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
        setCalendarPager()
        setBackdrop()
    }

    private fun setCalendarPager() {
        if (adapter != null) {
            return
        }

        viewBinding.homeCalendarPager.apply {
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    scrollDispatcher.saveScrollPosition(
                        pagerSnapHelper.findTargetSnapPosition(recyclerView.layoutManager, dx, dy)
                    )
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    recyclerView.adapter?.itemCount?.let {
                        scrollDispatcher.onScrollStateChanged(newState, viewModel, it)
                    }
                }
            })
            adapter = HomeCalendarPageAdapter(viewModel)
            scrollToPosition(1)
        }

        adapter = viewBinding.homeCalendarPager.adapter as HomeCalendarPageAdapter
    }

    private fun setNavigation() {
        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
            openCalendarDetail(it)
        })

        viewModel.addNewPage.observe(viewLifecycleOwner, EventObserver {
            (viewBinding.homeCalendarPager.adapter as HomeCalendarPageAdapter).submitList(
                viewModel.yearMonthMap.value?.keys,
                it
            )
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = HomeFragmentDirections.actionHomeFragmentToDayListFragment(item)
        findNavController().navigate(action)
    }

    private fun setBackdrop() {
        val sheetBehavior = BottomSheetBehavior.from(viewBinding.backdropLayout)
        sheetBehavior.isFitToContents = false
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        viewModel.selectedMonth.observe(viewLifecycleOwner, Observer {
            val location = intArrayOf(0, 0)
            viewBinding.homeCalendarPager.getLocationOnScreen(location)

            if (location[1] == 0) {
                return@Observer
            }

            val anchorViewBottom =
                (location[1] + viewBinding.homeCalendarPager.height).toFloat() / resources.displayMetrics.heightPixels

            sheetBehavior.halfExpandedRatio = 1 - anchorViewBottom
        })
    }
}
