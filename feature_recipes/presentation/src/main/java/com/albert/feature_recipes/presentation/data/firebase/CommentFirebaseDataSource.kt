package com.albert.feature_recipes.presentation.data.firebase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.albert.feature_recipes.data.datasorce.CommentRemoteDataSource
import com.albert.feature_recipes.domain.CommentModel
import com.albert.feature_recipes.domain.ErrorModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentFirebaseDataSource @Inject constructor() : CommentRemoteDataSource {
    override suspend fun getCommentsOfPlace(idPlace: Int): Flow<Either<ErrorModel, List<CommentModel>>> =
        try {
            callbackFlow {
                val databaseFirebase =
                    Firebase.database.reference.child("comments").child(idPlace.toString())
                val listener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val listComments = ArrayList<RemoteComment>()
                        snapshot.children.map { postSnapshot ->
                            postSnapshot.getValue(RemoteComment::class.java)
                                ?.let { listComments.add(it) }
                        }
                        listComments.sortByDescending { it.timeRegister }
                        trySend(listComments.toDomain().right())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(ErrorModel.Unknown(error.message).left())
                    }
                }
                databaseFirebase.addValueEventListener(listener)
                awaitClose { databaseFirebase.removeEventListener(listener) }
            }

        } catch (e: Exception) {
            flow { e.message?.let { ErrorModel.Unknown(it).left() } }
        }

    override suspend fun saveCommentOfPlace(comment: CommentModel) {
        val databaseFirebase = Firebase.database
        val commentReference =
            databaseFirebase.getReference("comments").child(comment.idRecipe.toString())
        commentReference.push().setValue(comment)
    }

    private fun RemoteComment.toDomain() = CommentModel(
        idRecipe,
        stars,
        idUser,
        timeRegister,
        nameUser,
        commentText
    )

    fun List<RemoteComment>.toDomain() = map { it.toDomain() }

    private fun CommentModel.fromDomainModel() = RemoteComment(
        idRecipe,
        stars,
        idUser,
        timeRegister,
        nameUser,
        commentText
    )
}