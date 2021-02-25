package com.klewerro.radommap.data

import androidx.lifecycle.LiveData

interface BaseRepository {
    fun getAllInterestCategories(): LiveData<List<InterestCategory>>
    fun getAllInterestPoints(): LiveData<List<InterestPoint>>
}