package com.albert.feature_recipes.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.albert.feature_recipes.presentation.databinding.FragmentBlankBinding

class BlankFragment : Fragment(R.layout.fragment_blank) {
    lateinit var binding: FragmentBlankBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlankBinding.bind(view)
    }
}