package com.jp.babyfood.ui.calendardetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.databinding.RowIngredientAddBinding
import com.jp.babyfood.databinding.RowIngredientBinding
import com.jp.babyfood.util.LogUtil
import com.jp.babyfood.util.LogUtil.LOGI

class IngredientAdapter(val viewModel: CalendarDetailViewModel) :
    ListAdapter<Ingredient, RecyclerView.ViewHolder>(IngredientDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ROW = 0
        const val VIEW_TYPE_ADD = 1
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_ADD else VIEW_TYPE_ROW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ADD) {
            return AddIngredientViewHolder.from(parent)
        }
        return IngredientViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        LOGI("BF_TAG", "bind item : $itemCount, $position")
        if (itemCount - 1 == position) {
            return
        }
        (holder as IngredientViewHolder).bind(viewModel, getItem(position))
    }

    class IngredientViewHolder(private val binding: RowIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CalendarDetailViewModel, item: Ingredient) {
            binding.viewModel = viewModel
            binding.ingredient = item
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

    class AddIngredientViewHolder(private val binding: RowIngredientAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CalendarDetailViewModel, item: Ingredient) {
            binding.viewModel = viewModel
            binding.ingredient = item
        }

        companion object {
            fun from(parent: ViewGroup): AddIngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return AddIngredientViewHolder(
                    RowIngredientAddBinding.inflate(
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
        LogUtil.LOGI("BF_TAG", "areItemsTheSame : ${oldItem.name}, ${newItem.name}")
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        LogUtil.LOGI("BF_TAG", "areContentsTheSame : $oldItem, $newItem")
        return oldItem == newItem
    }
}
