package com.jp.babyfood.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan
import java.lang.ref.WeakReference


class CustomImageSpan(mDrawable: Drawable) : DynamicDrawableSpan() {
    private var mDrawableRef: WeakReference<Drawable?>? =
        null

    init {
        mDrawableRef = WeakReference(mDrawable)
    }

    override fun getSize(
        paint: Paint?,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        val d = getCachedDrawable()
        val rect: Rect = d!!.bounds
        if (fm != null) {
            fm.ascent = -rect.bottom
            fm.descent = 0
            fm.top = fm.ascent
            fm.bottom = 0
        }
        return rect.right
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val b = getCachedDrawable()
        canvas.save()
        var transY = bottom - b!!.bounds.bottom / 2
        val fm = paint.fontMetricsInt
        transY -= fm.descent - fm.ascent / 2
        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }

    override fun getDrawable(): Drawable? {
        return mDrawableRef!!.get()
    }

    private fun getCachedDrawable(): Drawable? {
        val wr = mDrawableRef
        var d: Drawable? = null
        if (wr != null) d = wr.get()
        if (d == null) {
            d = drawable
            mDrawableRef = WeakReference(d)
        }
        return d
    }
}