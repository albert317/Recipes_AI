package com.albert.feature_recipes.presentation.ui.home

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.basicDiffUtil
import com.albert.feature_recipes.presentation.common.inflate
import com.albert.feature_recipes.presentation.common.loadUrl
import com.albert.feature_recipes.presentation.databinding.ItemRecipeBinding
import java.text.Normalizer
import java.util.Locale

class RecipesAdapter(private val listener: (RecipeModel) -> Unit) :
    ListAdapter<RecipeModel, RecipesAdapter.ViewModelRecipe>(
        basicDiffUtil { old, new -> old.id == new.id }), Filterable {
    private var itemsFull: List<RecipeModel> = emptyList()

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
        fun bind(recipe: RecipeModel) {
            binding.textTitle.text = recipe.name
            recipe.image?.let { binding.image.loadUrl(it) }
            binding.textDescription.text = recipe.description
            binding.textOrigin.text = recipe.origin
        }
    }

    fun setItems(items: List<RecipeModel>) {
        itemsFull = items
        submitList(items)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val normalizedFilterPattern = normalizeString(constraint.toString())
                val filteredList = if (normalizedFilterPattern.isBlank()) {
                    itemsFull
                } else {
                    itemsFull.filter { item ->
                        normalizeString(item.name).contains(normalizedFilterPattern)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList = results?.values as? List<RecipeModel> ?: emptyList()
                submitList(filteredList)
            }
        }
    }

    private fun normalizeString(input: String): String {
        val inputAux = input.lowercase(Locale.getDefault())
        val normalizedString = Normalizer.normalize(inputAux, Normalizer.Form.NFD)
        return normalizedString.replace("\\p{M}".toRegex(), "")
    }
}