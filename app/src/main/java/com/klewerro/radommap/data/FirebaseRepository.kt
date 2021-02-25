package com.klewerro.radommap.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepository: BaseRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getAllInterestCategories(): LiveData<List<InterestCategory>> {
        val result = MutableLiveData<List<InterestCategory>>()
        firestore.collection("categories")
            .get()
            .addOnSuccessListener {
                val categories = it.toObjects(InterestCategory::class.java)
                result.postValue(categories)
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestCategories: Exception - ${it.message}", it)
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
            }
            .addOnFailureListener {
                Log.e(TAG, "getAllInterestPoints: Exception - ${it.message}", it)
            }

        return result
    }


    companion object {
        const val TAG = "FirebaseRepository"
    }
}