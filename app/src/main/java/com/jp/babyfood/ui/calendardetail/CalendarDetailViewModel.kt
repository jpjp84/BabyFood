package com.jp.babyfood.ui.calendardetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _food = MutableLiveData(Food("test1", "", mutableListOf(), -1, ""))
    val food: LiveData<Food> = _food

    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    fun updateIngredients(food: Food?) {
        //TODO Get Ingredient Data by Day
        _ingredients.value =
            arrayListOf(Ingredient("aa", 10), Ingredient("bb", 15), Ingredient("cc", 10))
    }

    fun saveIngredients() {
        _food.value?.ingredients = ingredients.value!!
    }
}