package com.albert.feature_recipes.presentation.common

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

open class BaseFragment(layout: Int) : Fragment(layout) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    protected open fun onBackPressed() {
        view?.let { Navigation.findNavController(it).navigateUp() }
    }
}