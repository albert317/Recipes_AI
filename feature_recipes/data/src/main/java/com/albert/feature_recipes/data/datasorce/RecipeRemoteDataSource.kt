package com.albert.feature_recipes.data.datasorce

import arrow.core.Either
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RecipeRemoteDataSource {
    suspend fun getRecipes(): Flow<Either<ErrorModel, List<RecipeModel>>>
}