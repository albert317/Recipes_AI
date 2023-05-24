package com.albert.feature_recipes.presentation.ui.home

import app.cash.turbine.test
import com.albert.feature_recipes.data.RecipesRepository
import com.albert.feature_recipes.presentation.CoroutinesTestRule
import com.albert.feature_recipes.presentation.ui.FakeRemoteDataSource
import com.albert.feature_recipes.presentation.ui.FakeRemoteDataSourceError
import com.albert.feature_recipes.presentation.ui.defaultFakeRecipes
import com.albert.feature_recipes.presentation.ui.errorModel
import com.albert.feature_recipes.presentation.ui.home.HomeViewModel.UiState
import com.albert.feature_recipes.usecase.GetRecipesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `A0-WHEN data is loaded from remote data source is successful THEN validate state UiState and valid that the two lists are equal`() =
        runTest {
            //GIVEN
            val remoteDataSource = FakeRemoteDataSource()
            val recipesRepository = RecipesRepository(remoteDataSource)
            val getRecipesUseCase = GetRecipesUseCase(recipesRepository)
            val viewModel = HomeViewModel(getRecipesUseCase)

            //WHEN
            viewModel.state.test {
                //THEN
                assertEquals(UiState(), awaitItem())
                assertEquals(
                    UiState(recipes = emptyList(), loading = true, error = null), awaitItem()
                )
                assertEquals(
                    UiState(recipes = defaultFakeRecipes, loading = false, error = null),
                    awaitItem()
                )

                cancel()
            }
        }

    @Test
    fun `A1-WHEN data is loaded from remote data source is error THEN validate state UiState and valid that the ErrorModel`() =
        runTest {
            //GIVEN
            val remoteDataSource = FakeRemoteDataSourceError()
            val recipesRepository = RecipesRepository(remoteDataSource)
            val getRecipesUseCase = GetRecipesUseCase(recipesRepository)
            val viewModel = HomeViewModel(getRecipesUseCase)

            //WHEN
            viewModel.state.test {
                //THEN
                assertEquals(UiState(), awaitItem())
                assertEquals(
                    UiState(recipes = emptyList(), loading = true, error = null),
                    awaitItem()
                )
                assertEquals(
                    UiState(recipes = emptyList(), loading = false, error = errorModel),
                    awaitItem()
                )
                cancel()
            }
        }
}