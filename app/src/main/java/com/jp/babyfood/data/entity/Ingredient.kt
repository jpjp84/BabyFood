package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Ingredient constructor(val name: String, val gram: Int) : Parcelable