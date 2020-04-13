package com.jp.babyfood.ui.calendardetail

import android.content.Context
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.view.WordToChipTextWatcher
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    fun getWordToChipTextWatcher(context: Context): TextWatcher {
        return WordToChipTextWatcher(context)
    }

    fun updateIngredients(day: Day) {
        //TODO Get Ingredient Data by Day

        _ingredients.value =
            arrayListOf(Ingredient("aa", 10), Ingredient("bb", 15), Ingredient("cc", 10))
    }
}