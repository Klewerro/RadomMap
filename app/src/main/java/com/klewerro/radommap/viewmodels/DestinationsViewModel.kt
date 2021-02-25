package com.klewerro.radommap.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klewerro.radommap.data.BaseRepository
import com.klewerro.radommap.data.DestinationsRepository
import com.klewerro.radommap.data.InterestCategory
import com.klewerro.radommap.data.InterestPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    repository: BaseRepository
)  : ViewModel() {

    private val interestPoints = repository.getAllInterestPoints()
    val interestCategories = repository.getAllInterestCategories()

    private val _selectedCategory = MutableLiveData<InterestCategory>()
    val selectedCategory: LiveData<InterestCategory> = _selectedCategory

    private val _categoryInterestPoints = MutableLiveData<List<InterestPoint>>()
    val categoryInterestPoints: LiveData<List<InterestPoint>> = _categoryInterestPoints


    fun setSelectedCategory(position: Int) {
        val selectedCategory = interestCategories.value!!.get(position)
        _selectedCategory.postValue(selectedCategory)
        _categoryInterestPoints.postValue(getSelectedInterestPoints(selectedCategory.id!!))
    }


    private fun getSelectedInterestPoints(id: String): List<InterestPoint> {
        val list = interestPoints.value?.filter { it.categoryId == id }
        return list!!
    }
}