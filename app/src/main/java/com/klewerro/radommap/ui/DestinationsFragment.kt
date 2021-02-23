package com.klewerro.radommap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.klewerro.radommap.databinding.FragmentDestinationsBinding
import com.klewerro.radommap.viewmodels.DestinationsViewModel

class DestinationsFragment : Fragment() {

    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DestinationsViewModel by viewModels()
    private val destinationsRecyclerAdapter = DestinationsRecyclerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.destinationsRecyclerView.apply {
            adapter = destinationsRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.interestPoints.observe(viewLifecycleOwner) { interestCategories ->
            val points = interestCategories.flatMap { it.points }
            destinationsRecyclerAdapter.setList(points)
        }
    }

    override fun onDestroyView() {
        //Avoid memory leak
        super.onDestroyView()
        _binding = null
    }
}