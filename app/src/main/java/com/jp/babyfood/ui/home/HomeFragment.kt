package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.ui.calendardetail.CalendarDetailFragment
import com.jp.babyfood.ui.daylist.DayListFragment
import com.jp.babyfood.util.EventObserver
import com.jp.babyfood.util.dispatchers.CalendarBackdropDispatcher
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import javax.inject.Inject


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    CalendarBackdropDispatcher.OpenFragment {

    @Inject
    lateinit var scrollDispatcher: HomePagerScrollDispatcher

    private val backdropDispatcher by lazy {
        CalendarBackdropDispatcher(
            viewBinding.backdropLayout,
            this
        )
    }

    private var adapter: HomeCalendarPageAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarPager()
        setBackdropHandler()
        setNavigation()
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
        viewModel.addNewPage.observe(viewLifecycleOwner, EventObserver {
            (viewBinding.homeCalendarPager.adapter as HomeCalendarPageAdapter).submitList(
                viewModel.yearMonthMap.value?.keys,
                it
            )
        })

        viewModel.selectedMonth.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = "${it.month} ${it.year}"
        })

        viewModel.selectedDay.observe(viewLifecycleOwner, Observer {
            backdropDispatcher.showDayListFragment()
        })

        viewModel.addNewFood.observe(viewLifecycleOwner, EventObserver {
            backdropDispatcher.showCalendarFragment()
        })
    }

    private fun setBackdropHandler() {
        childFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getString("bundleKey")
            backdropDispatcher.showCalendarFragment()
        }
    }

    override fun attachDayListFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.backdrop_fragment, DayListFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun attachCalendarFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.backdrop_fragment, CalendarDetailFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
