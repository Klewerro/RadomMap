package com.klewerro.radommap.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepository: BaseRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val _downloadStatus = MutableLiveData<Int>(0)
    val downloadStatus: LiveData<Int> = _downloadStatus

    override fun getAllInterestCategories(): LiveData<List<InterestCategory>> {
        val result = MutableLiveData<List<InterestCategory>>()
        firestore.collection("categories")
            .get()
            .addOnSuccessListener {
                val categories = it.toObjects(InterestCategory::class.java)
                result.postValue(categories)
                increaseStatus(downloadStatus.value!!)
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestCategories: Exception - ${it.message}", it)
                _downloadStatus.postValue(-1)
            }

        return result
    }

    override fun getAllInterestPoints(): LiveData<List<InterestPoint>> {
        val result = MutableLiveData<List<InterestPoint>>()
        firestore.collection("points")
            .get()
            .addOnSuccessListener {
                val points = it.toObjects(InterestPoint::class.java)
                result.postValue(points)
                increaseStatus(downloadStatus.value!!)
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestPoints: Exception - ${it.message}", it)
                _downloadStatus.postValue(-1)
            }

        return result
    }

    private fun increaseStatus(value: Int) {
        if (_downloadStatus.value == -1)
            _downloadStatus.value = 0

        //_downloadStatus.postValue(value + 1)  // Doesn't work, because it's scheduling setting value
                                                // instead of setting it immediately
        _downloadStatus.value = value + 1
    }

    companion object {
        const val TAG = "FirebaseRepository"
    }
}