package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Day constructor(val date: String, var foods: List<Food>, var disable: Boolean = false) :
    Parcelable {
    fun getDateToClass(): Int {
        return Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyyMMdd", Locale.KOREA).parse(date)
        }.get(Calendar.DATE)
    }
}

typealias Days = List<Day>