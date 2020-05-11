package com.jp.babyfood.data.entity

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    var id: String = "",
    var title: String = "",
    var ingredients: MutableList<Ingredient> = mutableListOf(),
    var allergy: Boolean = false,
    var emesis: Boolean = false,
    var diarrhea: Boolean = false,
    var comment: String? = "",
    var color: String = "#00000000"
) : Parcelable, BaseObservable() {

}