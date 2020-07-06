package com.jp.babyfood.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentHomeBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.ui.daylist.DayListFragmentDirections
import com.jp.babyfood.util.EventObserver
import com.jp.babyfood.util.behavior.LockableBottomSheetBehavior
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.orhanobut.logger.Logger
import javax.inject.Inject


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    @Inject
    lateinit var scrollDispatcher: HomePagerScrollDispatcher

    private var adapter: HomeCalendarPageAdapter? = null

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarPager()
        setNavigation()
        openCalendarDetail()
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
            val bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.backdropLayout);
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            Logger.d("${findNavController()}")
        })

        viewModel.addNewFood.observe(viewLifecycleOwner, EventObserver {

        })
    }

    private fun openCalendarDetail() {
        val behavior: LockableBottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(viewBinding.backdropLayout) as LockableBottomSheetBehavior
        behavior.addBottomSheetCallback(object: BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED){
                    behavior.isLock = false
                }
                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    behavior.isLock = true
                    val action = DayListFragmentDirections.actionDayListFragmentToCalendarDetailFragment()
                    Logger.d("${activity?.findNavController(R.id.nav_host_fragment2)} open")
                    activity?.findNavController(R.id.nav_host_fragment2)?.popBackStack()
                    behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                }
            }
        })

        activity?.findNavController(R.id.nav_host_fragment2)
            ?.addOnDestinationChangedListener { controller, destination, arguments ->
                Logger.d("openCalender : $controller, $destination")
                val behavior: LockableBottomSheetBehavior<ConstraintLayout> =
                    BottomSheetBehavior.from(viewBinding.backdropLayout) as LockableBottomSheetBehavior
                behavior.state = BottomSheetBehavior.STATE_EXPANDED

                behavior.isLock = false
            }
    }
}
