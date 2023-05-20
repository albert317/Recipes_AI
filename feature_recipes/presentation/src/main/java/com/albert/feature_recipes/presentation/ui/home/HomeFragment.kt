package com.albert.feature_recipes.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
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
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = RecipesAdapter(::listenerClickRecipe)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            adapter.setItems(it.recipes)
            binding.loading = it.loading
        }

        binding.apply {
            searchView.queryHint = getString(R.string.home_search)
            searchView.setOnQueryTextListener(this@HomeFragment)
        }
    }

    private fun listenerClickRecipe(recipe: RecipeModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(recipe.parcelable())
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?) = false
    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }
}