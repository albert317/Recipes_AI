package com.albert.feature_recipes.presentation.ui.home

import app.cash.turbine.test
import arrow.core.Either
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.CoroutinesTestRule
import com.albert.feature_recipes.presentation.ui.home.HomeViewModel.UiState
import com.albert.feature_recipes.usecase.GetRecipesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var viewModel: HomeViewModel

    private val recipes = listOf(providesRecipe())

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getRecipesUseCase)
    }

    @Test
    fun `A0-WHEN invoke getRecipesUseCase with recipes THEN getRecipe set loading to true, after updates recipes with listRecipes  and set loading to false`() =
        runTest {
            //GIVEN
            whenever(getRecipesUseCase()).thenReturn(flowOf(Either.Right(recipes)))
            //WHEN
            viewModel.state.test {
                //THEN
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(loading = false, recipes = recipes), awaitItem())
                cancel()
            }
        }

    @Test
    fun `A1-WHEN invoke getRecipesUseCase with ErrorModel THEN getRecipe set loading to true, after updates recipes with empty and set loading to false`() =
        runTest {
            //GIVEN
            whenever(getRecipesUseCase()).thenReturn(flowOf(Either.Left(ErrorModel.Unknown("Error"))))
            //WHEN
            viewModel.state.test {
                //THEN
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(loading = false, recipes = ArrayList()), awaitItem())
                cancel()
            }
        }

    @Test
    fun `A2-WHEN invoke init {getRecipe()} THEN verify action calls the corresponding use case`() =
        runTest {
            //WHEN
            whenever(getRecipesUseCase()).thenReturn(flowOf(Either.Right(recipes)))
            runCurrent()
            //THEN
            verify(getRecipesUseCase).invoke()
        }

    private fun providesRecipe() = RecipeModel(
        id = 0,
        name = "Ceviche",
        description = "el ceviche es un plato típico de la gastronomía peruana. Consiste en pescado",
        preparation = "preparation: 1. Cortar el pescado o mariscos en trozos pequeños. 2. Mezclar con j",
        image = "ceviche.jpg",
        origin = "Perú",
        latitude = -9.1899f,
        longitude = -75.0152f,
        ingredients = listOf(IngredientModel(name = "Pescado", amount = "200 gramos"))
    )

}