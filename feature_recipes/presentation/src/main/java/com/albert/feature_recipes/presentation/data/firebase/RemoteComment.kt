package com.albert.feature_recipes.presentation.data.firebase

data class RemoteComment(
    var idRecipe: Int = 0,
    var stars: Int? = 0,
    var idUser: Int? = 0,
    var timeRegister: String? = "",
    var nameUser: String? = "",
    var commentText: String? = "",
)