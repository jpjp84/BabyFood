package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentCalendarDetailBinding
import com.jp.babyfood.ui.base.BaseDialogFragment


class CalendarDetailFragment :
    BaseDialogFragment<CalendarDetailViewModel, FragmentCalendarDetailBinding>() {

    private val args: CalendarDetailFragmentArgs by navArgs()

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_Popup)
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

        setCollapsingActionBar()
        setIngredientAdapter()
        viewBinding.toolbar.navigationIcon =
            resources.getDrawable(android.R.drawable.ic_menu_close_clear_cancel)
        viewBinding.toolbar.title = "Test"

        viewModel.updateIngredients(args.food)
    }

    private fun setCollapsingActionBar() {
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    private fun setIngredientAdapter() {
        viewBinding.ingredientList.adapter = IngredientAdapter(viewModel)
    }
}