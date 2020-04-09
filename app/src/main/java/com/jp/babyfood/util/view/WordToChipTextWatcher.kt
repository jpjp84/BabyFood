package com.jp.babyfood.util.view

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ImageSpan
import com.google.android.material.chip.ChipDrawable
import com.jp.babyfood.R

class WordToChipTextWatcher(val context: Context) : TextWatcher {

    private var spannedString: Int = 0

    override fun afterTextChanged(editable: Editable) {
        val editableStr = editable.toString()

        if (TextUtils.isEmpty(editableStr)) {
            spannedString = 0
            return
        }

        if (isPressEnter(editableStr)) {
            editable.delete(editable.length - 1, editable.length)
            editable.setSpan(
                getSpanChip(editable),
                spannedString,
                editable.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannedString = editable.length
        }
    }

    private fun isPressEnter(editableStr: String): Boolean =
        editableStr.subSequence(editableStr.length - 1, editableStr.length) == "\n"

    private fun getSpanChip(editable: Editable): ImageSpan {
        val chip = ChipDrawable.createFromResource(context, R.xml.chip)
        chip.text = editable.subSequence(spannedString, editable.length)
        chip.setBounds(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)
        return ImageSpan(chip)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}