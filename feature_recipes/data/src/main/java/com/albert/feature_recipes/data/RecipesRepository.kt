package com.albert.feature_recipes.data

import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import javax.inject.Inject

class RecipesRepository @Inject constructor(
    private val remoteDataSource: RecipeRemoteDataSource,
) {
    suspend fun getRecipes() = remoteDataSource.getRecipes()
}