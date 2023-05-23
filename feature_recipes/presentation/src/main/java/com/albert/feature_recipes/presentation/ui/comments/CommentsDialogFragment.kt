package com.albert.feature_recipes.presentation.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsDialogFragment : Fragment() {
    private val viewModel: CommentsViewModel by viewModels()
    private var idRecipe: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val commentsState by viewModel.state.collectAsState()
                commentsState.comment?.let { CommentList(comments = it, ::sendComment) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idRecipe = arguments?.getInt("idRecipe") ?: 0
        idRecipe.let { viewModel.getComments(it) }
    }

    private fun sendComment(commentText: String) {
        idRecipe.let { viewModel.saveComments(commentText, it) }
    }
}