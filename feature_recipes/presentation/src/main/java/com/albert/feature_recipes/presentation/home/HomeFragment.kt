package com.albert.feature_recipes.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.text.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBlankFragment()
            findNavController().navigate(action)
        }

    }


}