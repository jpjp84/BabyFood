package com.jp.babyfood.data.entity

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Ingredient constructor(
    var name: String = "",
    var gram: Int = 0,
    var allergyCount: Int = 0,
    var emesisCount: Int = 0,
    var diarrheaCount: Int = 0
) : Parcelable,
    BaseObservable() {

    var gramStr: String
        @Bindable get() = gram.toString()
        set(value) {
            try {
                this.gram = value.toInt()
            } catch (e: NumberFormatException) {
                this.gram = 0
            }
        }
}