package com.jp.babyfood.util

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener


class KeyboardUtil(act: Activity, contentView: View) {
    private var decorView: View = act.window.decorView

    private var onGlobalLayoutListener = OnGlobalLayoutListener {
        val rect = Rect()
        decorView.apply {
            getWindowVisibleDisplayFrame(rect)
            val height: Int = context.resources.displayMetrics.heightPixels
            val diff: Int = height - rect.bottom

            if (diff == 0 && contentView.paddingBottom != 0) {
                contentView.setPadding(0, 0, 0, 0)
            }

            if (diff != 0 && contentView.paddingBottom != diff) {
                contentView.setPadding(0, 0, 0, diff)
            }
        }
    }

    fun enable() = decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)

    fun disable() = decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
}