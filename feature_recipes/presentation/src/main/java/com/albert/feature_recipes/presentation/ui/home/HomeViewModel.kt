package com.albert.feature_recipes.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.usecase.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true, error = null) }
            val recipes = getRecipesUseCase()
            recipes.flowOn(Dispatchers.IO).collect {
                it.fold(
                    ifLeft = { error ->
                        _state.update { _state.value.copy(loading = false, error = error) }
                    }, ifRight = { recipes ->
                        _state.update { _state.value.copy(loading = false, recipes = recipes) }
                    })
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val recipes: List<RecipeModel> = ArrayList(),
        val error: ErrorModel? = null,
    )
}