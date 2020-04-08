package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Day constructor(val date: Int, val work: String) : Parcelable