package com.albert.feature_recipes.domain

data class CommentModel(
    var idRecipe: Int = 0,
    var stars: Int? = 0,
    var idUser: Int? = 0,
    var timeRegister: String? = "",
    var nameUser: String? = "",
    var commentText: String? = "",
)