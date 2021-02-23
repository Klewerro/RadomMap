package com.klewerro.radommap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.klewerro.radommap.R
import com.klewerro.radommap.databinding.FragmentDestinationsBinding

class DestinationsFragment : Fragment() {

    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_destinationsFragment_to_mapFragment)
        }
    }

    override fun onDestroyView() {
        //Avoid memory leak
        super.onDestroyView()
        _binding = null
    }
}