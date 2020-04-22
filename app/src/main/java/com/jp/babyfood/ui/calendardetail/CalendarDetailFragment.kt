package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentCalendarDetailBinding
import com.jp.babyfood.ui.base.BaseFragment


class CalendarDetailFragment :
    BaseFragment<CalendarDetailViewModel, FragmentCalendarDetailBinding>() {

    private val args: CalendarDetailFragmentArgs by navArgs()

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollapsingActionBar()
        setIngredientAdapter()

        viewModel.updateIngredients(args.day)
    }

    private fun setCollapsingActionBar() {
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    private fun setIngredientAdapter() {
        viewBinding.ingredientList.adapter = IngredientAdapter(viewModel)
    }
}