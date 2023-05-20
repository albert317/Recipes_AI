package com.albert.feature_recipes.presentation.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.ui.home.RecipesAdapter


@BindingAdapter("items")
fun RecyclerView.setItems(places: List<RecipeModel>?) {
    if (places != null) {
        (adapter as? RecipesAdapter)?.submitList(places)
    }
}
@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}