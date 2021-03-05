package com.klewerro.radommap.viewmodels

import androidx.lifecycle.*
import com.klewerro.radommap.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val state: SavedStateHandle
)  : ViewModel() {

    // LiveData
    private val interestPoints = repository.interestPoints
    private val interestCategories = repository.interestCategories

    private val _selectedCategory = MutableLiveData<InterestCategory>()
    val selectedCategory: LiveData<InterestCategory> = _selectedCategory

    private val _categoryInterestPoints = MutableLiveData<List<InterestPoint>>()
    val categoryInterestPoints: LiveData<List<InterestPoint>> = _categoryInterestPoints

    val mediatorLiveDataCategories = interestCategories.combineWith(interestPoints) { categories, points ->
        if (categories != null && points != null) {
            initSelectedCategory()
        }
        categories
    }

    val downloadStatus = repository.downloadStatus

    // States
    var selectedSpinnerPosition = state.get<Int>(STATE_SPINNER_POSITION) ?: 0
        set(value) {
            field = value
            state.set(STATE_SPINNER_POSITION, value)
        }



    fun setSelectedCategory(position: Int) {
        if (selectedSpinnerPosition == - 1) {
            selectedSpinnerPosition = 0
            return
        }

        if (interestCategories.value?.isNotEmpty() == true) {
            val selectedCategory = interestCategories.value?.get(position)
            _selectedCategory.postValue(selectedCategory)
            _categoryInterestPoints.postValue(getSelectedInterestPoints(selectedCategory?.id!!))
            selectedSpinnerPosition = position
        }
    }

    fun reFetchData() {
        repository.resetDownloadStatus()
        repository.getAllInterestPoints()
        repository.getAllInterestCategories()
    }


    private fun initSelectedCategory() {
        if (selectedSpinnerPosition == - 1) {
            selectedSpinnerPosition = 0
        }

        if (interestCategories.value?.isNotEmpty() == true) {
            val selectedCategory = interestCategories.value?.get(selectedSpinnerPosition)
            _selectedCategory.postValue(selectedCategory)
            _categoryInterestPoints.postValue(getSelectedInterestPoints(selectedCategory?.id!!))
        }
    }

    private fun getSelectedInterestPoints(id: String): List<InterestPoint> {
        val list = interestPoints.value?.filter { it.categoryId == id }
        return list!!
    }

    private fun <T, K, R> LiveData<T>.combineWith(
        liveData: LiveData<K>,
        block: (T?, K?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block(this.value, liveData.value)
        }
        result.addSource(liveData) {
            result.value = block(this.value, liveData.value)
        }
        return result
    }

    companion object {
        private const val STATE_SPINNER_POSITION = "spinnerPositionState"
        private const val STATE_EDITTEXT_STATE = "etTextState"
    }
}