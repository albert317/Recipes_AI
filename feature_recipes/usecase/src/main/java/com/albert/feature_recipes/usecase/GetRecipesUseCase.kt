package com.albert.feature_recipes.usecase

import com.albert.feature_recipes.data.RecipesRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val repository: RecipesRepository) {
    suspend operator fun invoke() = repository.getRecipes()
}