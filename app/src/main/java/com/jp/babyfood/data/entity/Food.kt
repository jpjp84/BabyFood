package com.jp.babyfood.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    var id: String = "",
    var title: String = "",
    var ingredients: List<Ingredient>? = mutableListOf(),
    var allergy: Int? = -1,
    var comment: String? = "",
    var color: String = "#00000000"
) : Parcelable