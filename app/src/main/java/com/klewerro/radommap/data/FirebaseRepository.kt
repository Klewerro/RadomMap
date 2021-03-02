package com.klewerro.radommap.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository: BaseRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val _interestPoints = MutableLiveData<List<InterestPoint>>()
    override val interestPoints: LiveData<List<InterestPoint>> = _interestPoints

    private val _interestCategories = MutableLiveData<List<InterestCategory>>()
    override val interestCategories: LiveData<List<InterestCategory>> = _interestCategories

    private val _downloadStatus = MutableLiveData<Int>(0)
    override val downloadStatus: LiveData<Int> = _downloadStatus

    var test = true

    init {
        getAllInterestCategories()
        getAllInterestPoints()
    }


    override fun getAllInterestCategories() {
        //val result = MutableLiveData<List<InterestCategory>>()
        firestore.collection("categories")
            .get()
            .addOnSuccessListener {
                val categories = it.toObjects(InterestCategory::class.java)
                _interestCategories.postValue(categories)
                increaseStatus(downloadStatus.value!!)
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestCategories: Exception - ${it.message}", it)
                _downloadStatus.postValue(-1)
            }
    }

    override fun getAllInterestPoints() {
        //val result = MutableLiveData<List<InterestPoint>>()
        firestore.collection("points")
            .get()
            .addOnSuccessListener {
                val points = it.toObjects(InterestPoint::class.java)
                if (test) {
                    test = false
                    _downloadStatus.postValue(-1)
                } else {
                    _interestPoints.postValue(points)
                    increaseStatus(downloadStatus.value!!)
                }

            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestPoints: Exception - ${it.message}", it)
                _downloadStatus.postValue(-1)
            }
    }

    override fun increaseStatus(value: Int) {
        if (_downloadStatus.value != -1)
            _downloadStatus.value = value + 1
    }

    override fun resetDownloadStatus() {
        _downloadStatus.value = 0
    }

    companion object {
        const val TAG = "FirebaseRepository"
    }
}