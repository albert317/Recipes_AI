package com.albert.feature_recipes.data

import arrow.core.Either
import com.albert.feature_recipes.data.datasorce.RecipeRemoteDataSource
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.IngredientModel
import com.albert.feature_recipes.domain.RecipeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class) //para generar nuevo mock en cada pase al setup, osea cada test
class RecipesRepositoryTest {
    @Mock
    private lateinit var remoteDataSource: RecipeRemoteDataSource

    private lateinit var recipesRepository: RecipesRepository

    @Before
    fun setup() {
        recipesRepository = RecipesRepository(remoteDataSource)
    }

    @Test
    fun `getRecipes should return a list of RecipeModel when successful`() = runTest {
        //GIVEN
        val recipeList = listOf(getRecipe())
        val flow = flowOf(Either.Right(recipeList))
        whenever(remoteDataSource.getRecipes()).thenReturn(flow)

        //WHEN
        val result = recipesRepository.getRecipes()

        //THEN
        assertEquals(flow, result)
    }

    @Test
    fun `getRecipes should return a ErrorModel of RecipeModel when error`() = runTest {
        //GIVEN
        val flow = flowOf(Either.Left(ErrorModel.Unknown("Error")))
        whenever(remoteDataSource.getRecipes()).thenReturn(flow)

        //WHEN
        val result = recipesRepository.getRecipes()

        //THEN
        assertEquals(flow, result)
    }

    private fun getRecipe() = RecipeModel(
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