package com.albert.feature_recipes.usecase

import com.albert.feature_recipes.data.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(idRecipe: Int) = repository.getComments(idRecipe)
}