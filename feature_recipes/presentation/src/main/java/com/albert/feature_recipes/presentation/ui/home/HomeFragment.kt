package com.albert.feature_recipes.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.albert.feature_recipes.domain.RecipeModel
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.launchAndCollect
import com.albert.feature_recipes.presentation.databinding.FragmentHomeBinding
import com.albert.feature_recipes.presentation.ui.model.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = RecipesAdapter(::listenerClickRecipe)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            adapter.submitList(it.recipes)
        }
        binding.recyclerView.adapter = adapter

    }

    private fun listenerClickRecipe(recipe: RecipeModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(recipe.parcelable())
        findNavController().navigate(action)
    }
}