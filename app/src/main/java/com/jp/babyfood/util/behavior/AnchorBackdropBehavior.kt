package com.jp.babyfood.util.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jp.babyfood.R

class AnchorBackdropBehavior<V : View>(val context: Context, attrs: AttributeSet?) :
    BottomSheetBehavior<V>(context, attrs) {

    private var anchorViewId: Int = 0
    private var isSetAnchor = false

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.AnchorBackdropBehavior)
            val hasAnchorTarget =
                typedArray.hasValue(R.styleable.AnchorBackdropBehavior_anchorTarget)
            if (hasAnchorTarget) {
                anchorViewId = typedArray.getResourceId(
                    R.styleable.CollapsingEditTextBehavior_collapsedTarget,
                    0
                )
            }
            typedArray.recycle()
        }

        isFitToContents = false
        state = STATE_COLLAPSED
    }
//
//    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
//        return dependency is ConstraintLayout
//    }
//
//    override fun onDependentViewChanged(
//        parent: CoordinatorLayout,
//        child: V,
//        dependency: View
//    ): Boolean {
//        setAnchor(dependency)
//
//        return super.onDependentViewChanged(parent, child, dependency)
//    }
//
//    private fun setAnchor(dependency: View) {
//        if (anchorViewId == 0 || isSetAnchor) {
//            return
//        }
//
//        setHalfExpandedRatio(dependency.findViewById<RecyclerView>(anchorViewId))
//        isSetAnchor = true
//    }
//
//    fun setHalfExpandedRatio(view: View) {
//        val location = intArrayOf(0, 0)
//        view.getLocationOnScreen(location)
//
//        if (location[1] == 0) {
//            return
//        }
//
//        val anchorViewBottom =
//            (location[1] + view.height).toFloat() / context.resources.displayMetrics.heightPixels
//
//        Logger.d("Recycler View Layout Change : $anchorViewBottom")
//        halfExpandedRatio = 1 - anchorViewBottom
//    }
}