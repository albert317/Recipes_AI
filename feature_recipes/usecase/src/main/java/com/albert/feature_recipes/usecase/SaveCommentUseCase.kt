package com.albert.feature_recipes.usecase

import com.albert.feature_recipes.data.CommentRepository
import com.albert.feature_recipes.domain.CommentModel
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(commentModel: CommentModel) = repository.saveComment(commentModel)
}