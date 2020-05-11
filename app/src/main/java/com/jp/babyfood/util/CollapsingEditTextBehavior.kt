/**
 * origin code
 * https://github.com/klarson2/CustomCoordinatorLayoutBehavior/blob/master/app/src/main/java/com/krislarson/customcoordinatorlayoutbehavior/CollapsingImageBehavior.java
 */
package com.jp.babyfood.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.alpha
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import com.google.android.material.appbar.AppBarLayout
import com.jp.babyfood.R


class CollapsingEditTextBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View?>() {
    private var targetId = 0
    private var collapsedTextSize = 0f
    private lateinit var viewInfo: FloatArray
    private lateinit var targetViewInfo: FloatArray

    init {
        attrs?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.CollapsingEditTextBehavior)
            targetId =
                typedArray.getResourceId(R.styleable.CollapsingEditTextBehavior_collapsedTarget, 0)
            collapsedTextSize =
                typedArray.getDimension(
                    R.styleable.CollapsingEditTextBehavior_collapsedTextSize,
                    getDefaultCollapsedTextSize(context.resources.displayMetrics)
                )

            typedArray.recycle()
        }
        check(targetId != 0) { "collapsedTarget attribute not specified on view for behavior" }
    }

    private fun getDefaultCollapsedTextSize(displayMetrics: DisplayMetrics): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            18f,
            displayMetrics
        )
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        setup(parent, child, dependency)

        val appBarLayout = dependency as AppBarLayout
        val scrollRange = appBarLayout.totalScrollRange
        val distanceFactor = (appBarLayout.y * -1) / scrollRange

        child.x = calculateAttrByFactor(distanceFactor, X)
        child.y = calculateAttrByFactor(distanceFactor, Y)
        child.layoutParams = (child.layoutParams as CoordinatorLayout.LayoutParams).apply {
            this.width = calculateAttrByFactor(distanceFactor, WIDTH).toInt()
            this.height = calculateAttrByFactor(distanceFactor, HEIGHT).toInt()
        }

        //modify : change text size when child is edittext
        if (child is EditText) {
            child.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                calculateAttrByFactor(distanceFactor, TEXT_SIZE)
            )
        }

        //modify : change background color alpha when child.background is ColorDrawable
        if (child.background is ColorDrawable) {
            child.background.alpha = calculateAttrToZeroByFactor(distanceFactor, BG_ALPHA)
        }

        //modify : change padding to zero alpha when exist child padding
        if (viewInfo[PADDING] > 0) {
            child.setPadding(calculateAttrToZeroByFactor(distanceFactor, PADDING))
        }

        return true
    }

    private fun setup(parent: CoordinatorLayout, child: View, dependency: View) {
        if (::viewInfo.isInitialized) {
            return
        }

        viewInfo = FloatArray(7).apply {
            this[X] = child.x
            //modify : Always at the bottom of AppBarLayout
            this[Y] = (dependency.height - child.height).toFloat() - child.marginBottom
            this[WIDTH] = child.width.toFloat()
            this[HEIGHT] = child.height.toFloat()

            if (child is EditText) {
                this[TEXT_SIZE] = child.textSize
            }

            if (child.background is ColorDrawable) {
                this[BG_ALPHA] = (child.background as ColorDrawable).color.alpha.toFloat()
            }

            if (child.paddingTop > 0) {
                this[PADDING] = child.paddingTop.toFloat()
            }
        }

        targetViewInfo = FloatArray(5).apply {
            val target: View =
                parent.findViewById(targetId)
                    ?: throw IllegalStateException("target view not found")

            this[WIDTH] += target.width.toFloat()
            this[HEIGHT] += target.height.toFloat()

            if (child is EditText) {
                this[TEXT_SIZE] = collapsedTextSize
            }

            findCoordinatorLayoutPosition(this, parent, target)
        }
    }

    private fun findCoordinatorLayoutPosition(
        floatArray: FloatArray,
        parent: CoordinatorLayout,
        target: View
    ) {
        var view: View = target

        while (view !== parent) {
            floatArray[X] += view.x
            floatArray[Y] += view.y
            view = view.parent as View
        }
    }

    private fun calculateAttrByFactor(factor: Float, attrIndex: Int): Float {
        return (viewInfo[attrIndex] + (factor * (targetViewInfo[attrIndex] - viewInfo[attrIndex])))
    }

    private fun calculateAttrToZeroByFactor(factor: Float, attrIndex: Int): Int {
        return (viewInfo[attrIndex] - (viewInfo[attrIndex] * factor)).toInt()
    }

    companion object {
        private const val X = 0
        private const val Y = 1
        private const val WIDTH = 2
        private const val HEIGHT = 3
        private const val TEXT_SIZE = 4
        private const val BG_ALPHA = 5
        private const val PADDING = 6
    }
}