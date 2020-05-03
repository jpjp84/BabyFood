/**
 * origin code
 * https://github.com/klarson2/CustomCoordinatorLayoutBehavior/blob/master/app/src/main/java/com/krislarson/customcoordinatorlayoutbehavior/CollapsingImageBehavior.java
 */
package com.jp.babyfood.util

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
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

        if (child is EditText) {
            child.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                calculateAttrByFactor(distanceFactor, TEXT_SIZE)
            )
        }

        return true
    }

    private fun setup(parent: CoordinatorLayout, child: View, dependency: View) {
        if (::viewInfo.isInitialized) {
            return
        }

        viewInfo = FloatArray(5).apply {
            this[X] = child.x
            //modify : Always at the bottom of AppBarLayout
            this[Y] = (dependency.height - child.height).toFloat() - child.marginBottom
            this[WIDTH] = child.width.toFloat()
            this[HEIGHT] = child.height.toFloat()

            if (child is EditText) {
                this[TEXT_SIZE] = child.textSize
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

    companion object {
        private const val X = 0
        private const val Y = 1
        private const val WIDTH = 2
        private const val HEIGHT = 3
        private const val TEXT_SIZE = 4
    }
}