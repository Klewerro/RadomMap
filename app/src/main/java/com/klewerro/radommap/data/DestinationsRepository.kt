package com.klewerro.radommap.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DestinationsRepository: BaseRepository {

    override fun getAllInterestCategories(): LiveData<List<InterestCategory>> {
        val result = MutableLiveData<List<InterestCategory>>()

        result.postValue(listOf(
            InterestCategory("1", "Kategoria 1"),
            InterestCategory("2", "Kategoria 2"),
            InterestCategory("3", "Kategoria 3")
        ))

        return result
    }

    override fun getAllInterestPoints(): LiveData<List<InterestPoint>> {
        val result = MutableLiveData<List<InterestPoint>>()
        val i1 = InterestPoint("1", "Punkt1", "Opis wybranej lokalizacji 1", "https://www.google.pl/", null, "1" )
        val i2 = InterestPoint("2", "Punkt2", "Opis wybranej lokalizacji 2", "https://www.google.pl/",null, "1")
        val i3 = InterestPoint("3", "Punkt3", "Opis wybranej lokalizacji 3", "https://www.google.pl/",null, "2")
        val i4 = InterestPoint("4", "Punkt4", "Opis wybranej lokalizacji 4", "https://www.google.pl/",null, "2")
        val i5 = InterestPoint("5", "Punkt5", "Opis wybranej lokalizacji 5", "https://www.google.pl/",null, "3")
        val i6 = InterestPoint("6", "Punkt6", "Opis wybranej lokalizacji 6", "https://www.google.pl/",null, "3")
        result.postValue(listOf(i1, i2, i3, i4, i5, i6))

        return result
    }

}