package com.albert.feature_recipes.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.albert.feature_recipes.presentation.CoroutinesTestRule
import com.albert.feature_recipes.presentation.ui.detail.DetailViewModel.UiState
import com.albert.feature_recipes.presentation.ui.model.IngredientParcelable
import com.albert.feature_recipes.presentation.ui.model.RecipeParcelable
import com.albert.feature_recipes.presentation.ui.model.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle


    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("recipe", providesRecipeParcelable())
        }
        viewModel = DetailViewModel(savedStateHandle)
    }

    @Test
    fun `A0-WHEN init viewModel THEN getRecipe, set loading true, after update recipe, and set loading false`() =
        runTest {
            //WHEN
            viewModel.state.test {
                //THEN
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(
                    UiState(loading = false,
                        recipe = providesRecipeParcelable().toModel()), awaitItem()
                )
            }
        }

    private fun providesRecipeParcelable() = RecipeParcelable(
        id = 0,
        name = "Ceviche",
        description = "el ceviche es un plato típico de la gastronomía peruana. Consiste en pescado",
        preparation = "preparation: 1. Cortar el pescado o mariscos en trozos pequeños. 2. Mezclar con j",
        image = "ceviche.jpg",
        origin = "Perú",
        latitude = -9.1899f,
        longitude = -75.0152f,
        ingredients = listOf(IngredientParcelable(name = "Pescado", amount = "200 gramos"))
    )
}