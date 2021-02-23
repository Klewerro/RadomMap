package com.klewerro.radommap.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.klewerro.radommap.data.InterestPoint

class MapViewModel(state: SavedStateHandle) : ViewModel() {

    val interestPoint = state.get<InterestPoint>("interestPoint")

}