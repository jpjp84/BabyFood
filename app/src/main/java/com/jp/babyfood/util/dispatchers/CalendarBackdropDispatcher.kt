package com.jp.babyfood.util.dispatchers

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.jp.babyfood.util.behavior.LockableBottomSheetBehavior

class CalendarBackdropDispatcher(val layout: View, val openFragment: OpenFragment) {

    val behavior: LockableBottomSheetBehavior<View> by lazy {
        from(layout) as LockableBottomSheetBehavior
    }

    init {
        behavior.isLock = true
        behavior.state = STATE_COLLAPSED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == STATE_EXPANDED) {
                    behavior.isLock = false
                }
                if (newState == STATE_HALF_EXPANDED) {
                    openFragment.attachDayListFragment()
                }
            }
        })
    }

    fun showDayListFragment() {
        layout.post { behavior.state = STATE_HALF_EXPANDED }
    }

    fun showCalendarFragment() {
        openFragment.attachCalendarFragment()
        layout.post { behavior.state = STATE_EXPANDED }
    }

    interface OpenFragment {
        fun attachDayListFragment()
        fun attachCalendarFragment()
    }
}
