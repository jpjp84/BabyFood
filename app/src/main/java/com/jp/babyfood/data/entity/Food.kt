package com.jp.babyfood.data.entity

data class Food(
    var title: String,
    var ingredient: List<Ingredient>,
    var allergy: Int,
    var comment: String
)