package com.jp.babyfood.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.RadioButton
import com.jp.babyfood.R

class GridRadioGroup(context: Context, attrs: AttributeSet?) : GridLayout(context, attrs),
    View.OnClickListener {

    private var defaultCheckedBtnId: Int = 0
    private var checkedRadioBtn: RadioButton? = null

    init {
        //check default checkedButton
        attrs?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.GridRadioGroup)
            defaultCheckedBtnId =
                typedArray.getResourceId(R.styleable.GridRadioGroup_checkedButton, 0)

            typedArray.recycle()
        }
    }

    override fun addView(child: View?) {
        super.addView(child)

        setChildClickListener(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)

        setChildClickListener(child)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        super.addView(child, width, height)

        setChildClickListener(child)
    }

    private fun setChildClickListener(child: View?) {
        if (child !is RadioButton) {
            return
        }

        child.apply {
            if (id == defaultCheckedBtnId) {
                isChecked = true
                checkedRadioBtn = child
            }
            child.setOnClickListener(this@GridRadioGroup)
        }
    }

    override fun onClick(child: View) {
        checkedRadioBtn?.let {
            it.isChecked = false
        }

        checkedRadioBtn = (child as RadioButton).apply {
            isChecked = true
        }
    }
}