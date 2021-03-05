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

    private val _downloadStatus = MutableLiveData(DownloadStatus.STARTED)
    override val downloadStatus: LiveData<DownloadStatus> = _downloadStatus

    init {
        getAllInterestCategories()
        getAllInterestPoints()
    }


    override fun getAllInterestCategories() {
        firestore.collection("categories")
            .get()
            .addOnSuccessListener {
                val categories = it.toObjects(InterestCategory::class.java)

                if (it.metadata.isFromCache && categories.isEmpty()) {
                    _downloadStatus.postValue(DownloadStatus.ERROR)
                } else {
                    _interestCategories.postValue(categories)
                    increaseStatus(downloadStatus.value!!)
                    if (it.metadata.isFromCache)
                        _downloadStatus.postValue(DownloadStatus.CACHE)
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestCategories: Exception - ${it.message}", it)
                _downloadStatus.postValue(DownloadStatus.ERROR)
            }
    }

    override fun getAllInterestPoints() {
        firestore.collection("points")
            .get()
            .addOnSuccessListener {
                val points = it.toObjects(InterestPoint::class.java)

                if (it.metadata.isFromCache && points.isEmpty()) {
                    _downloadStatus.postValue(DownloadStatus.ERROR)
                } else {
                    _interestPoints.postValue(points)
                    increaseStatus(downloadStatus.value!!)
                    if (it.metadata.isFromCache)
                        _downloadStatus.postValue(DownloadStatus.CACHE)
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestPoints: Exception - ${it.message}", it)
                _downloadStatus.postValue(DownloadStatus.ERROR)
            }
    }

    override fun increaseStatus(value: DownloadStatus) {
        if (_downloadStatus.value != DownloadStatus.ERROR)
            _downloadStatus.value = DownloadStatus.getByValue(value.value + 1)
    }

    override fun resetDownloadStatus() {
        _downloadStatus.value = DownloadStatus.STARTED
    }

    companion object {
        const val TAG = "FirebaseRepository"
    }
}