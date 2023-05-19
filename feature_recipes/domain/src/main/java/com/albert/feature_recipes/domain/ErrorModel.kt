package com.albert.feature_recipes.domain

sealed interface ErrorModel {
    class Server(val code: Int) : ErrorModel
    object Connectivity : ErrorModel
    class Unknown(val message: String) : ErrorModel
}