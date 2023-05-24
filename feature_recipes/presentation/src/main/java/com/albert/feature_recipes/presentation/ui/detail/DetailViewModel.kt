package com.albert.feature_recipes.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albert.feature_recipes.domain.CommentModel
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.ui.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getRecipe()
    }

    private fun getRecipe() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            val recipe = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).recipe
            _state.update { _state.value.copy(recipe = recipe.toModel(), loading = false) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val recipe: RecipeModel? = null,
        val error: ErrorModel? = null,
    )
}