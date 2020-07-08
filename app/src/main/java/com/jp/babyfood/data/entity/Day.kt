package com.jp.babyfood.data.entity

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jp.babyfood.BR
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*

@Parcelize
data class Day constructor(
    val date: String,
    var foods: List<Food>,
    var disable: Boolean = false
) : Parcelable, BaseObservable() {

    fun getDateToClass(): Int {
        return Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyyMMdd", Locale.KOREA).parse(date)
        }.get(Calendar.DATE)
    }

    fun getYearMonth(): YearMonth {
        return YearMonth.of(date.substring(0..3).toInt(), date.substring(4).toInt())
    }

    @IgnoredOnParcel
    var select: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.select)
        }
}

typealias Days = List<Day>