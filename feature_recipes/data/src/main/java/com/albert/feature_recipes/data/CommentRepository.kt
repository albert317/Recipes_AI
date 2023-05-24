package com.albert.feature_recipes.data

import com.albert.feature_recipes.data.datasorce.CommentRemoteDataSource
import com.albert.feature_recipes.domain.CommentModel
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val remoteDataSource: CommentRemoteDataSource,
) {
    suspend fun getComments(idRecipe: Int) =
        remoteDataSource.getCommentsOfPlace(idRecipe)

    suspend fun saveComment(commentModel: CommentModel) =
        remoteDataSource.saveCommentOfPlace(commentModel)
}