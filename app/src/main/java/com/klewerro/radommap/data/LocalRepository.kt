package com.klewerro.radommap.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocalRepository: BaseRepository {

    private val _interestPoints = MutableLiveData<List<InterestPoint>>()
    override val interestPoints: LiveData<List<InterestPoint>> = _interestPoints

    private val _interestCategories = MutableLiveData<List<InterestCategory>>()
    override val interestCategories: LiveData<List<InterestCategory>> = _interestCategories

    private val _downloadStatus = MutableLiveData<Int>(0)
    override val downloadStatus: LiveData<Int> = _downloadStatus

    private var forceFirstFetchFailure = false  //true

    init {
        getAllInterestCategories()
        getAllInterestPoints()
    }


    override fun getAllInterestCategories() {
        _interestCategories.postValue(listOf(
            InterestCategory("1", "Kategoria 1"),
            InterestCategory("2", "Kategoria 2"),
            InterestCategory("3", "Kategoria 3")

        ))
        increaseStatus(downloadStatus.value!!)
    }

    override fun getAllInterestPoints() {
        if (forceFirstFetchFailure) {
            forceFirstFetchFailure = false
            _downloadStatus.postValue(-1);
        } else {
            val i1 = InterestPoint("1", "Punkt1", "Opis wybranej lokalizacji 1", "https://www.google.pl/", null, "1" )
            val i2 = InterestPoint("2", "Punkt2", "Opis wybranej lokalizacji 2", "https://www.google.pl/",null, "1")
            val i3 = InterestPoint("3", "Punkt3", "Opis wybranej lokalizacji 3", "https://www.google.pl/",null, "2")
            val i4 = InterestPoint("4", "Punkt4", "Opis wybranej lokalizacji 4", "https://www.google.pl/",null, "2")
            val i5 = InterestPoint("5", "Punkt5", "Opis wybranej lokalizacji 5", "https://www.google.pl/",null, "3")
            val i6 = InterestPoint("6", "Punkt6", "Opis wybranej lokalizacji 6", "https://www.google.pl/",null, "3")
            _interestPoints.postValue(listOf(i1, i2, i3, i4, i5, i6))
            increaseStatus(downloadStatus.value!!)
        }
    }

    override fun increaseStatus(value: Int) {
        if (_downloadStatus.value != -1)
            _downloadStatus.value = value + 1
    }

    override fun resetDownloadStatus() {
        _downloadStatus.value = 0
    }
}