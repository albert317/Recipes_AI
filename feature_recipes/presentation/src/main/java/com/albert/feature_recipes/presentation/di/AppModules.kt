package com.albert.feature_recipes.presentation.di

import com.albert.feature_recipes.data.datasorce.CommentRemoteDataSource
import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import com.albert.feature_recipes.presentation.data.firebase.CommentFirebaseDataSource
import com.albert.feature_recipes.presentation.data.server.RecipeServerDataSource
import com.albert.feature_recipes.presentation.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService {
        val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        return Retrofit.Builder()
            .baseUrl("https://demo7087681.mockable.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindRemoteDataSourceRecipes(recipeServerDataSource: RecipeServerDataSource): RecipeRemoteDataSource

    @Binds
    abstract fun bindRemoteDataSourceComments(commentFirebaseDataSource: CommentFirebaseDataSource): CommentRemoteDataSource
}