package com.jp.babyfood.data.entity

import java.io.Serializable

data class Month(val month: Int, val days: MutableList<Day>) : Serializable