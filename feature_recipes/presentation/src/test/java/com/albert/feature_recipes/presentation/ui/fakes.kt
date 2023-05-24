package com.albert.feature_recipes.presentation.ui

import arrow.core.Either
import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.domain.RecipeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val defaultFakeRecipes = providesRecipes(1,2,3)
val errorModel = ErrorModel.Unknown("error")

class FakeRemoteDataSource : RecipeRemoteDataSource {
    override suspend fun getRecipes(): Flow<Either<ErrorModel, List<RecipeModel>>> =
        flowOf(Either.Right(defaultFakeRecipes))
}

class FakeRemoteDataSourceError : RecipeRemoteDataSource {
    override suspend fun getRecipes(): Flow<Either<ErrorModel, List<RecipeModel>>> =
        flowOf(Either.Left(errorModel))
}

fun providesRecipes(vararg id: Int) = id.map {
    providesRecipe().copy(id = it)
}

 fun providesRecipe() = RecipeModel(
    id = 0,
    name = "Ceviche",
    description = "el ceviche es un plato típico de la gastronomía peruana. Consiste en pescado",
    preparation = "preparation: 1. Cortar el pescado o mariscos en trozos pequeños. 2. Mezclar con j",
    image = "ceviche.jpg",
    origin = "Perú",
    latitude = -9.1899f,
    longitude = -75.0152f,
    ingredients = listOf(IngredientModel(name = "Pescado", amount = "200 gramos"))
)
