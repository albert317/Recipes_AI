package com.albert.feature_recipes.presentation.data.server

import retrofit2.http.GET

interface RemoteService {
    @GET("/recipes")
    suspend fun listRecipes(): RemoteResult
}