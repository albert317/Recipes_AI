package com.albert.feature_recipes.presentation.di

import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import com.albert.feature_recipes.presentation.data.server.RecipeServerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindRemoteDataSourceRecipes(recipeServerDataSource: RecipeServerDataSource): RecipeRemoteDataSource
}