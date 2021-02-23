package com.klewerro.radommap.viewmodels

import androidx.lifecycle.ViewModel
import com.klewerro.radommap.data.DestinationsRepository

class DestinationsViewModel : ViewModel() {

    private val repository = DestinationsRepository()
    val interestPoints = repository.geAllInterestPoints()


}