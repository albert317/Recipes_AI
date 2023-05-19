package com.albert.feature_recipes.presentation.data

import com.albert.feature_recipes.domain.ErrorModel
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): ErrorModel = when (this) {
    is IOException -> ErrorModel.Connectivity
    is HttpException -> ErrorModel.Server(code())
    else -> ErrorModel.Unknown(message ?: "")
}