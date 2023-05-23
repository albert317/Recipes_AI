package com.albert.feature_recipes.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.launchAndCollect
import com.albert.feature_recipes.presentation.common.loadUrl
import com.albert.feature_recipes.presentation.databinding.FragmentDetailBinding
import com.albert.feature_recipes.presentation.ui.comments.CommentsDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    lateinit var binding: FragmentDetailBinding
    private val adapter = IngredientsAdapter()
    private val viewModel: DetailViewModel by viewModels()
    lateinit var navController: NavController
    private var idRecipe: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        navController = findNavController()

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.apply {
                textName.text = it.recipe?.name
                textDescription.text = it.recipe?.description
                textOrigin.text = it.recipe?.origin
                textPreparation.text = it.recipe?.preparation
                it.recipe?.image?.let { url -> image.loadUrl(url) }
                recyclerView.adapter = adapter
                back.setOnClickListener { navController.navigateUp() }
                map.setOnClickListener { _ -> goMap(it.recipe) }
                comments.setOnClickListener { goComment() }
                idRecipe = it.recipe?.id
            }
            adapter.submitList(it.recipe?.ingredients)
        }
    }

    private fun goComment() {
        idRecipe?.let {
            val action = DetailFragmentDirections.actionDetailFragmentToCommentsDialogFragment(it)
            navController.navigate(action)
        }
    }

    private fun goMap(recipeModel: RecipeModel?) {
        recipeModel?.let {
            val action = DetailFragmentDirections.actionDetailFragmentToMapFragment(
                recipeModel.name,
                recipeModel.origin ?: "",
                recipeModel.latitude ?: 0f,
                recipeModel.longitude ?: 0f
            )
            navController.navigate(action)
        }
    }
}