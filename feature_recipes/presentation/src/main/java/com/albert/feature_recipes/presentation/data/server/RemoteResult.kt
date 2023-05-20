package com.albert.feature_recipes.presentation.data.server

import com.google.gson.annotations.SerializedName

data class RemoteResult(
    @SerializedName("page") val page: Int,
    @SerializedName("result") val result: List<RemoteRecipe>,
)

data class RemoteRecipe(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("preparation") val preparation: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("latitude") val latitude: Float?,
    @SerializedName("longitude") val longitude: Float?,
    @SerializedName("ingredients") val ingredients: List<IngredientRecipe>?,
)

data class IngredientRecipe(
    @SerializedName("name") val name: String,
    @SerializedName("amount") val amount: String?,
)