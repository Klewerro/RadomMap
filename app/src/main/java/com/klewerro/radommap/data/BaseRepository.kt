package com.klewerro.radommap.data

import androidx.lifecycle.LiveData

interface BaseRepository {
    val interestPoints: LiveData<List<InterestPoint>>
    val interestCategories: LiveData<List<InterestCategory>>
    val downloadStatus: LiveData<Int>

    fun getAllInterestCategories()
    fun getAllInterestPoints()
    fun increaseStatus(value: Int)
    fun resetDownloadStatus()
}