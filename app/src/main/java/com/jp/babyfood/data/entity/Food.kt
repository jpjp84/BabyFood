package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    var id: String,
    var title: String,
    var ingredients: List<Ingredient>?,
    var allergy: Int?,
    var comment: String?
) : Parcelable