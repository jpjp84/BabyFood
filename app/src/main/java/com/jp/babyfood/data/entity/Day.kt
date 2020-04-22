package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Day constructor(val date: Long, val work: String) : Parcelable {
    fun getDateToClass(): Int {
        return Calendar.getInstance().apply {
            this.timeInMillis = date
        }.get(Calendar.DATE)
    }
}