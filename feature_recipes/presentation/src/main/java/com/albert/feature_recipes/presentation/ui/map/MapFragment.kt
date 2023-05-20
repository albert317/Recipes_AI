package com.albert.feature_recipes.presentation.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.common.launchAndCollect
import com.albert.feature_recipes.presentation.common.toIcon
import com.albert.feature_recipes.presentation.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map){

    lateinit var binding: FragmentMapBinding
    lateinit var googleMap: GoogleMap
    lateinit var navController: NavController
    private val viewModel: MapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
        navController = findNavController()

        val manager = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        manager.getMapAsync { map ->
            googleMap = map
            viewLifecycleOwner.launchAndCollect(viewModel.state) {
                initToolbar(it.recipeName, it.recipeOrigin)
                addMarker(LatLng(it.latitude.toDouble(), it.longitude.toDouble()), it.recipeOrigin)
            }
        }
    }

    private fun initToolbar(name: String?, origin: String?) {
        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_white_24)
        toolbar.title = name
        toolbar.subtitle = origin
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    private fun addMarker(position: LatLng, name: String?) {
        val marker = MarkerOptions()
            .position(position)
            .title(name)
            .icon(resources.toIcon(R.drawable.img))
        val camaraPosition = CameraPosition.builder()
            .target(position)
            .zoom(4f)
            .tilt(30f)
            .build()
        googleMap.addMarker(marker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camaraPosition))
    }
}