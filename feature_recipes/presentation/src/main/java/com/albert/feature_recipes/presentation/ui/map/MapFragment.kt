package com.albert.feature_recipes.presentation.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.albert.feature_recipes.presentation.R
import com.albert.feature_recipes.presentation.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(R.layout.fragment_map),OnMapReadyCallback,
    GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    lateinit var binding: FragmentMapBinding
    lateinit var mMap:GoogleMap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        val manager=childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        manager.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap=googleMap
        mMap.setOnMapClickListener(this)
        mMap.setOnMapLongClickListener(this)
        val peru=LatLng(-12.050949, -77.052552)
        mMap.addMarker(MarkerOptions().position(peru).title("Peru"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru))
    }

    override fun onMapClick(p0: LatLng) {

    }

    override fun onMapLongClick(p0: LatLng) {

    }
}