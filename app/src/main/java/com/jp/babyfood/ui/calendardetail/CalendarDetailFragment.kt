package com.jp.babyfood.ui.calendardetail

import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.ChipDrawable
import com.jp.babyfood.R
import com.jp.babyfood.databinding.FragmentCalendarDetailBinding
import com.jp.babyfood.ui.base.BaseFragment
import com.jp.babyfood.util.LogUtil.LOGI


class CalendarDetailFragment :
    BaseFragment<CalendarDetailViewModel, FragmentCalendarDetailBinding>() {

    private val args: CalendarDetailFragmentArgs by navArgs()

    override fun getViewModelClass(): Class<CalendarDetailViewModel> =
        CalendarDetailViewModel::class.java

    override fun getViewLayoutRes(): Int = R.layout.fragment_calendar_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var SpannedLength = 0

        viewBinding.ingredientEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                val str = editable.toString()
                LOGI("BF_TAG", "afterText : $str, SpannedLength : $SpannedLength")

                if (!TextUtils.isEmpty(str) && str.subSequence(
                        str.length - 1,
                        str.length
                    ) == "\n"
                ) {
                    editable.delete(editable.length - 1, editable.length)

                    val chip = ChipDrawable.createFromResource(requireContext(), R.xml.chip)
                    chip.text = editable.subSequence(SpannedLength, editable.length)
                    chip.setBounds(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)

                    val span = ImageSpan(chip)
                    editable.setSpan(
                        span,
                        SpannedLength,
                        editable.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    SpannedLength = editable.length
                }
                if (TextUtils.isEmpty(str)) {
                    SpannedLength = 0
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (charSequence?.length == SpannedLength - chipLength) {
//                    SpannedLength = charSequence?.length
//                }
            }

        })
        LOGI("BF_TAG", "Day : ${args.day}")
    }
}