package com.albert.feature_recipes.presentation.ui.detail

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.basicDiffUtil
import com.albert.feature_recipes.presentation.common.inflate
import com.albert.feature_recipes.presentation.databinding.ItemIngredientBinding

class IngredientsAdapter() :
    ListAdapter<IngredientModel, IngredientsAdapter.ViewModelIngredient>(basicDiffUtil { old, new -> old == new }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelIngredient {
        val view = parent.inflate(R.layout.item_ingredient, false)
        return ViewModelIngredient(view)
    }

    override fun onBindViewHolder(holder: ViewModelIngredient, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient, position)
    }

    inner class ViewModelIngredient(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientBinding.bind(itemView)
        fun bind(ingredient: IngredientModel, position: Int) {
            binding.textName.text = ingredient.name
            binding.textAmount.text = ingredient.amount
            binding.linearLayout.setBackgroundColor(
                getColorBackground(position)
            )
        }

        private fun getColorBackground(position: Int): Int {
            return if (position % 2 == 0) {
                itemView.resources.getColor(
                    R.color.grey_100,
                    null
                )
            } else {
                Color.TRANSPARENT
            }
        }
    }
}