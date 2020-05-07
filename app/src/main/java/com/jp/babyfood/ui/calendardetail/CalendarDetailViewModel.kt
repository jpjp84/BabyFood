package com.jp.babyfood.ui.calendardetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.LogUtil.LOGI
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _addIngredient = MutableLiveData<Event<Int>>()
    val addIngredient = _addIngredient

    private val _removeIngredient = MutableLiveData<Event<Int>>()
    val removeIngredient = _removeIngredient

    private val _food = MutableLiveData(Food("test1", "", mutableListOf(), -1, ""))
    val food: LiveData<Food> = _food

    private val _ingredients = MutableLiveData<MutableList<Ingredient>>()
    val ingredients: LiveData<MutableList<Ingredient>> = _ingredients

    fun updateIngredients(food: Food?) {
        //TODO Get Ingredient Data by Day
        _ingredients.value =
            arrayListOf(Ingredient("aa", 10), Ingredient("bb", 15), Ingredient("cc", 10))
    }

    fun addIngredient() {
        _ingredients.value?.let {
            it.add(Ingredient())
            _addIngredient.value = Event(it.size.minus(1))
        }
    }

    fun removeIngredient(ingredient: Ingredient) {
        _ingredients.value?.let {
            val removePosition = it.indexOf(ingredient)
            it.remove(ingredient)
            _removeIngredient.value = Event(removePosition)
        }
    }

    fun saveFood() {
        LOGI("BF_TAG", "Save Ingredient : ${food}")
    }
}