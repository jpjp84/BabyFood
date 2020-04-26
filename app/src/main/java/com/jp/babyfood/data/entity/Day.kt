package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import okhttp3.internal.format
import java.util.*

@Parcelize
data class Day constructor(val date: String, val foods: List<Food>, var disable: Boolean = false) :
    Parcelable {
    fun getDateToClass(): Int {
        return Calendar.getInstance().apply {
            format("yyyyMMdd", date)
        }.get(Calendar.DATE)
    }
}

typealias Days = List<Day>