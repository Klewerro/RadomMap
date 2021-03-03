package com.klewerro.radommap.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.klewerro.radommap.MainActivity
import com.klewerro.radommap.databinding.FragmentMapBinding
import com.klewerro.radommap.utils.ExtensionFunctions.setFragmentSubtitle
import com.klewerro.radommap.utils.MetricsUtil.convertPixelsToPx
import com.klewerro.radommap.viewmodels.MapViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.apply {
            onCreate(savedInstanceState)
            onResume()
            getMapAsync(this@MapFragment)
        }

        viewModel.categoryName.observe(viewLifecycleOwner) { categoryName ->
            setFragmentSubtitle(categoryName)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setPadding(0, 0, 0, 50.convertPixelsToPx())

        viewModel.interestPoint.observe(viewLifecycleOwner) { interestPoint ->
            val markerPosition = interestPoint.coordinates?.let { LatLng(it.latitude, it.longitude) }
            if (markerPosition != null) {
                val titleText = "${interestPoint.coordinates.latitude}; ${interestPoint.coordinates.longitude}"
                googleMap.addMarker(MarkerOptions().position(markerPosition).title(titleText))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 15f))
            }

            binding.descriptionTextView.text = interestPoint.description
            if (interestPoint.url != null) {
                binding.urlTextView.isVisible = true
                binding.urlTextView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(interestPoint.url))
                    startActivity(intent)
                }
            }
        }
    }

    override fun onDestroyView() {
        //Avoid memory leak
        super.onDestroyView()
        _binding = null
    }
}