package com.albert.feature_recipes.domain

data class RecipeModel(
    val id:Int,
    val name: String,
    val description: String?,
    val preparation:String?,
    val image: String?,
    val origin: String?,
    val latitude: Float?,
    val longitude: Float?,
    val ingredients: List<IngredientModel>?
)