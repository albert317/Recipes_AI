package com.albert.feature_recipes.presentation.data.server

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.data.toError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeServerDataSource @Inject constructor(private val service: RemoteService) :
    RecipeRemoteDataSource {
    override suspend fun getRecipes(): Flow<Either<ErrorModel, List<RecipeModel>>> = try {
        flow {
            val result = service.listRecipes().result
            val resultModel = result.toDomainModel()
            emit(resultModel.right())
        }
    } catch (e: Exception) {
        flow { emit(e.toError().left()) }
    }
}

private fun List<RemoteRecipe>.toDomainModel(): List<RecipeModel> = map { it.toDomainModel() }

private fun RemoteRecipe.toDomainModel() = RecipeModel(
    id,
    name,
    description,
    preparation,
    image,
    origin,
    latitude,
    longitude,
    ingredients?.toIngredientDomainModel()
)

private fun List<RemoteIngredient>.toIngredientDomainModel(): List<IngredientModel> =
    map { it.toDomainModel() }


private fun RemoteIngredient.toDomainModel() = IngredientModel(
    name,
    amount
)
