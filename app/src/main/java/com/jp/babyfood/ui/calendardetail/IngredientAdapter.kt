package com.jp.babyfood.ui.calendardetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.databinding.RowIngredientBinding
import com.jp.babyfood.util.LogUtil.LOGI

class IngredientAdapter(val viewModel: CalendarDetailViewModel, val lifecycleOwner: LifecycleOwner) :
    ListAdapter<Ingredient, RecyclerView.ViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IngredientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as IngredientViewHolder).bind(viewModel, lifecycleOwner, getItem(position))
    }

    class IngredientViewHolder(private val binding: RowIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CalendarDetailViewModel, lifecycleOwner: LifecycleOwner, item: Ingredient) {
            binding.viewModel = viewModel
            binding.ingredient = item
            binding.ingredientExpandBtn.setLifecycleOwner(lifecycleOwner)
        }

        companion object {
            fun from(parent: ViewGroup): IngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return IngredientViewHolder(
                    RowIngredientBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }
        }
    }
}

class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        LOGI("BF_TAG", "areItemsTheSame : ${oldItem.name}, ${newItem.name}")
        return oldItem.name == newItem.name && oldItem.expand == newItem.expand
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        LOGI("BF_TAG", "areContentsTheSame : $oldItem, $newItem")
        return oldItem == newItem
    }
}
