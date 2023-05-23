package com.albert.feature_recipes.presentation.ui.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albert.feature_recipes.domain.CommentModel
import com.albert.feature_recipes.domain.ErrorModel
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.common.getDateTime
import com.albert.feature_recipes.presentation.common.getRandomName
import com.albert.feature_recipes.presentation.ui.detail.DetailViewModel
import com.albert.feature_recipes.usecase.GetCommentsUseCase
import com.albert.feature_recipes.usecase.SaveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun getComments(idRecipe: Int) {

        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            val recipes = getCommentsUseCase(idRecipe)
            recipes.flowOn(Dispatchers.IO).collect {
                it.fold(
                    ifLeft = { error ->
                        _state.update {
                            _state.value.copy(
                                loading = false, error = error, comments = emptyList()
                            )
                        }
                    }, ifRight = { comments ->
                        _state.update {
                            _state.value.copy(
                                loading = false, error = null, comments = comments
                            )
                        }
                    }
                )
            }
        }
    }

    fun saveComments(commentText: String, idRecipe: Int) {
        val commentModel = CommentModel(
            idRecipe = idRecipe,
            stars = Random.nextInt(1, 6),
            idUser = 1,
            timeRegister = getDateTime(),
            nameUser = getRandomName(),
            commentText = commentText
        )
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            saveCommentUseCase(commentModel)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val comments:List<CommentModel> = emptyList(),
        val error: ErrorModel? = null,
    )
}