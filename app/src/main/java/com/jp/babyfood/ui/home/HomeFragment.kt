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
import com.jp.babyfood.util.LogUtil.LOGI


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var adapter: HomeCalendarPageAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarPager()
        viewModel.initMonth()

        setNavigation()
    }

    private fun setCalendarPager() {
        adapter = adapter ?: HomeCalendarPageAdapter(viewModel).also {
            val pagerSnapHelper = PagerSnapHelper().also { helper ->
                helper.attachToRecyclerView(viewBinding.homeCalendarPager)
            }

            viewBinding.homeCalendarPager.adapter = it
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

                    if (newState != RecyclerView.SCROLL_STATE_IDLE || scrolledPosition != 0) {
                        return
                    }

                    viewModel.updatePrevMonth()
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
        viewModel.months.observe(viewLifecycleOwner, Observer {
            LOGI("BF_TAG", "change months")
            it?.let((adapter as HomeCalendarPageAdapter)::submitList)
            viewBinding.homeCalendarPager.adapter?.notifyItemInserted(0)
        })
    }

    private fun openCalendarDetail(item: Day) {
        val action = HomeFragmentDirections.actionCalendarPageFragmentToCalendarDetailFragment(item)
        findNavController().navigate(action)
    }
}