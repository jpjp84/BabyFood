package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.view.View
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentCalendarDetailBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.EventObserver
import com.jp.babyfood.util.KeyboardUtil


class CalendarDetailFragment :
    BaseFragment<CalendarDetailViewModel, FragmentCalendarDetailBinding>() {

//    private val args: CalendarDetailFragmentArgs by navArgs()

    private val keyboardUtil by lazy {
        activity?.let { KeyboardUtil(it, viewBinding.calendarDetailWrapper) }
    }

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
        setAdjustResizeByKeyboardUtil()
        setIngredientAdapter()
        setEventObserver()

//        args.food?.let { viewModel.updateIngredients(it) }
    }

    private fun setNavigation() {
//        viewBinding.toolbar.navigationIcon =
//            resources.getDrawable(android.R.drawable.ic_menu_close_clear_cancel, null)
    }

    private fun setAdjustResizeByKeyboardUtil() {
        keyboardUtil?.enable()
    }

    private fun setEventObserver() {
        viewModel.addIngredient.observe(viewLifecycleOwner, EventObserver {
            viewBinding.ingredientList.adapter?.notifyItemInserted(it)
        })

        viewModel.removeIngredient.observe(viewLifecycleOwner, EventObserver {
            viewBinding.ingredientList.adapter?.notifyItemRemoved(it)
        })
    }

    private fun setIngredientAdapter() {
        viewBinding.ingredientList.adapter = IngredientAdapter(viewModel, viewLifecycleOwner)
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardUtil?.disable()
    }
}