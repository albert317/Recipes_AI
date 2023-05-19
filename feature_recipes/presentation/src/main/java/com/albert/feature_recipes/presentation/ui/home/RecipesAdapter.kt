package com.albert.feature_recipes.presentation.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.basicDiffUtil
import com.albert.feature_recipes.presentation.common.bindUrl
import com.albert.feature_recipes.presentation.common.inflate
import com.albert.feature_recipes.presentation.databinding.ItemRecipeBinding

class RecipesAdapter(private val listener: (RecipeModel) -> Unit) :
    ListAdapter<RecipeModel, RecipesAdapter.ViewModelRecipe>(
        basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelRecipe {
        val view = parent.inflate(R.layout.item_recipe, false)
        return ViewModelRecipe(view)
    }

    override fun onBindViewHolder(holder: ViewModelRecipe, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener { listener(recipe) }
    }

    inner class ViewModelRecipe(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecipeBinding.bind(itemView)
        fun bind(recipeModel: RecipeModel) {
            binding.textTitle.text = recipeModel.name
            binding.image.bindUrl(recipeModel.image)
            binding.textDescription.text = recipeModel.description
            binding.textOrigin.text = recipeModel.origin
        }
    }
}