package com.albert.feature_recipes.presentation.ui.model

import android.os.Parcelable
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.domain.RecipeModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeParcelable(
    val id: Int,
    val name: String,
    val description: String?,
    val preparation: String?,
    val image: String?,
    val origin: String?,
    val latitude: Float?,
    val longitude: Float?,
    val ingredients: List<IngredientParcelable>?,
) : Parcelable

@Parcelize
data class IngredientParcelable(
    val name: String,
    val amount: String?,
) : Parcelable

fun RecipeModel.parcelable() = RecipeParcelable(
    id,
    name,
    description,
    preparation,
    image,
    origin,
    latitude,
    longitude,
    ingredients?.parcelable()
)

fun List<IngredientModel>.parcelable(): List<IngredientParcelable> = map { it.parcelable() }

fun IngredientModel.parcelable() = IngredientParcelable(
    name,
    amount
)

fun RecipeParcelable.toModel() = RecipeModel(
    id,
    name,
    description,
    preparation,
    image,
    origin,
    latitude,
    longitude,
    ingredients?.toModel()
)

fun List<IngredientParcelable>.toModel(): List<IngredientModel> = map { it.toModel() }
fun IngredientParcelable.toModel() = IngredientModel(
    name,
    amount
)