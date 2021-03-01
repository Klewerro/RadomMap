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

    val mediatorLiveDataCategories = interestCategories.combineWith(interestPoints) { categories, points ->
        categories
    }




    fun setSelectedCategory(position: Int) {
        val selectedCategory = interestCategories.value!!.get(position)
        _selectedCategory.postValue(selectedCategory)
        _categoryInterestPoints.postValue(getSelectedInterestPoints(selectedCategory.id!!))
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


}