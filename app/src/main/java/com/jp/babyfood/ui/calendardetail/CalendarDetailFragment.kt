package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentCalendarDetailBinding
import com.jp.babyfood.ui.base.BaseDialogFragment
import com.jp.babyfood.util.KeyboardUtil


class CalendarDetailFragment :
    BaseDialogFragment<CalendarDetailViewModel, FragmentCalendarDetailBinding>() {

    private val args: CalendarDetailFragmentArgs by navArgs()

    private val keyboardUtil by lazy {
        activity?.let { KeyboardUtil(it, viewBinding.calendarDetailWrapper) }
    }

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun getTheme(): Int {
        return R.style.AppTheme_Popup
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.attributes?.windowAnimations = R.style.FullscreenDialogAnimation
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setIngredientAdapter()
        setNavigation()
        setAdjustResizeByKeyboardUtil()

        viewModel.updateIngredients(args.food)
    }

    private fun setNavigation() {
        viewBinding.toolbar.navigationIcon =
            resources.getDrawable(android.R.drawable.ic_menu_close_clear_cancel, null)
    }

    private fun setIngredientAdapter() {
        viewBinding.ingredientList.adapter = IngredientAdapter(viewModel)
    }

    private fun setAdjustResizeByKeyboardUtil() {
        keyboardUtil?.enable()
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardUtil?.disable()
    }
}