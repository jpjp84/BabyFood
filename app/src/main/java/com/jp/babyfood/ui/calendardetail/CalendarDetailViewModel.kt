package com.jp.babyfood.ui.calendardetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.LogUtil.LOGI
import javax.inject.Inject

class CalendarDetailViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _addIngredient = MutableLiveData<Event<Int>>()
    val addIngredient = _addIngredient

    private val _removeIngredient = MutableLiveData<Event<Int>>()
    val removeIngredient = _removeIngredient

    private val _food = MutableLiveData(Food("test1", "", mutableListOf()))
    val food: LiveData<Food> = _food

    private val _ingredients = MutableLiveData<MutableList<Ingredient>>()
    val ingredients: LiveData<MutableList<Ingredient>> = _ingredients

    fun updateIngredients(food: Food) {
        addDisposable(
            foodRepository.getDailyFoodById(food.id, true)
                .subscribe(
                    { result -> setFoodDetail(result) },
                    { t -> Log.e("BF_TAG", "Exception : ", t) }
                )
        )
    }

    private fun setFoodDetail(food: Food) {
        _food.value = food
        _ingredients.value = food.ingredients
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
        food.value?.let {
            LOGI("BF_TAG", "it : $it")
        }
    }

    private fun checkAllergy(food: Food, increaseAllergy: Ingredient) {
        food.ingredients?.map { ingredient -> ingredient.allergyCount++ }
    }
}