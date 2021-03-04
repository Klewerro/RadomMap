package com.klewerro.radommap.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.klewerro.radommap.R
import com.klewerro.radommap.data.InterestPoint
import com.klewerro.radommap.databinding.FragmentDestinationsBinding
import com.klewerro.radommap.utils.ExtensionFunctions.setFragmentSubtitle
import com.klewerro.radommap.viewmodels.DestinationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinationsFragment : Fragment(), DestinationsRecyclerAdapter.OnDestinationClickListener {

    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DestinationsViewModel by viewModels()
    private val destinationsRecyclerAdapter = DestinationsRecyclerAdapter(this)
    private var isFirstItemSelection = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        // Removing category text from actionBar after navigate back
        setFragmentSubtitle(null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Loading saved state from VM
        binding.editText.setText(viewModel.editTextState)

        binding.destinationsRecyclerView.apply {
            adapter = destinationsRecyclerAdapter
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager(requireContext())
            } else {
                GridLayoutManager(requireActivity(), 2)
            }
            setHasFixedSize(true)
        }

        binding.categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(a: AdapterView<*>?, v: View?, position: Int, id: Long) {
                if (isFirstItemSelection) {
                    // Loading saved state from VM
                    isFirstItemSelection = false
                    binding.categoriesSpinner.setSelection(viewModel.selectedSpinnerPosition)
                    return
                }
                viewModel.setSelectedCategory(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.loadingLayout.tryAgainButton.setOnClickListener {
            viewModel.reFetchData()
        }

        viewModel.categoryInterestPoints.observe(viewLifecycleOwner) { interestPoints ->
            destinationsRecyclerAdapter.setList(interestPoints)
        }

        viewModel.mediatorLiveDataCategories.observe(viewLifecycleOwner) { categories ->
            if (categories != null) {
                binding.categoriesSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    categories.map { it.name }
                )
            }
        }

        viewModel.downloadStatus.observe(viewLifecycleOwner) { status ->
            when(status) {
                0 -> {
                    binding.contentLinearLayout.isVisible = false
                    binding.loadingLayout.root.isVisible = true
                    binding.loadingLayout.progressBar.isVisible = true
                    binding.loadingLayout.progressStatusTextView.text = getString(R.string.fetching_data, 0, 2)
                }
                1 -> {
                    binding.loadingLayout.progressStatusTextView.text = getString(R.string.fetching_data, 1, 2)
                }
                2 -> {
                    binding.contentLinearLayout.isVisible = true
                    binding.loadingLayout.root.isVisible = false
                }
                -1 -> {
                    binding.loadingLayout.progressBar.isVisible = false
                    binding.loadingLayout.progressStatusTextView.text = getString(R.string.error_fetching_data_try_again)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isFirstItemSelection = true
    }

    override fun onDestroyView() {
        // Avoid memory leak
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(interestPoint: InterestPoint) {
        val action = DestinationsFragmentDirections.actionDestinationsFragmentToMapFragment(
            interestPoint,
            interestPoint.name!!,
            viewModel.selectedCategory.value?.name
        )
        findNavController().navigate(action)
    }
}