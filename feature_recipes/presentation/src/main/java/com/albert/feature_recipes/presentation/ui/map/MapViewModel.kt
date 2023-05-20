package com.albert.feature_recipes.presentation.ui.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            val args = MapFragmentArgs.fromSavedStateHandle(savedStateHandle)
            _state.update {
                _state.value.copy(
                    loading = false,
                    latitude = args.latitude,
                    longitude = args.longitude,
                    recipeName = args.name,
                    recipeOrigin = args.origin
                )
            }

        }
    }

    data class UiState(
        val loading: Boolean = false,
        val latitude: Float = 0f,
        val longitude: Float = 0f,
        val recipeName: String? = null,
        val recipeOrigin: String? = null,
    )
}