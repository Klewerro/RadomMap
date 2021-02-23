package com.klewerro.radommap.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DestinationsRepository {

    fun geAllInterestPoints(): LiveData<List<InterestCategory>> {
        val result = MutableLiveData<List<InterestCategory>>()

        val i1 = InterestPoint(1, "Punkt1", "Opis wybranej lokalizacji 1", "https://www.google.pl/")
        val i2 = InterestPoint(2, "Punkt2", "Opis wybranej lokalizacji 2", "https://www.google.pl/")
        val i3 = InterestPoint(3, "Punkt3", "Opis wybranej lokalizacji 3", "https://www.google.pl/")
        val i4 = InterestPoint(4, "Punkt4", "Opis wybranej lokalizacji 4", "https://www.google.pl/")
        val i5 = InterestPoint(5, "Punkt5", "Opis wybranej lokalizacji 5", "https://www.google.pl/")
        val i6 = InterestPoint(6, "Punkt6", "Opis wybranej lokalizacji 6", "https://www.google.pl/")
        val data = listOf(
            InterestCategory("Kategoria 1", listOf(i1, i2)),
            InterestCategory("Kategoria 2", listOf(i3, i4)),
            InterestCategory("Kategoria 3", listOf(i5, i6))
        )

        result.postValue(data)

        return result
    }

}