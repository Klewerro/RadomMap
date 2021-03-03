package com.klewerro.radommap.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.klewerro.radommap.data.InterestPoint

class MapViewModel(state: SavedStateHandle) : ViewModel() {

    val interestPoint: LiveData<InterestPoint> = state.getLiveData<InterestPoint>("interestPoint")
    val categoryName: LiveData<String?> = state.getLiveData<String>("categoryName")

}