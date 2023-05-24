package com.albert.feature_recipes.data.datasorce

import arrow.core.Either
import com.albert.feature_recipes.domain.CommentModel
import com.albert.feature_recipes.domain.ErrorModel
import kotlinx.coroutines.flow.Flow

interface CommentRemoteDataSource {
    suspend fun getCommentsOfPlace(idPlace: Int): Flow<Either<ErrorModel, List<CommentModel>>>
    suspend fun saveCommentOfPlace(comment: CommentModel)
}