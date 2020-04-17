package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.EventObserver


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var adapter: HomeCalendarPageAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarPager()
        viewModel.updateCalendar()

        setNavigation()
    }

    private fun setCalendarPager() {
        adapter = adapter ?: HomeCalendarPageAdapter(viewModel).also {
            viewBinding.homeCalendarPager.adapter = it
            val pagerSnapHelper = PagerSnapHelper().also { helper ->
                helper.attachToRecyclerView(viewBinding.homeCalendarPager)
            }

            viewBinding.homeCalendarPager.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                var scrolledPosition = 0
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    scrolledPosition =
                        pagerSnapHelper.findTargetSnapPosition(recyclerView.layoutManager, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (scrolledPosition == 0 && viewModel.loadCalendar()) {
                            recyclerView.adapter?.notifyItemInserted(0)
                        }
                    }
                }
            }).apply {
                viewBinding.homeCalendarPager.scrollToPosition(1)
            }
        }
    }

    private fun setNavigation() {
        viewModel.openCalendarDetailEvent.observe(viewLifecycleOwner, EventObserver {
            openCalendarDetail(it)
        })
        viewModel.yearMonth.observe(viewLifecycleOwner, Observer {
            it?.let((adapter as HomeCalendarPageAdapter)::submitMap)
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = HomeFragmentDirections.actionCalendarPageFragmentToCalendarDetailFragment(item)
        findNavController().navigate(action)
    }
}