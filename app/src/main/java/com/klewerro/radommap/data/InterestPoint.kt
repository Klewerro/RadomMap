package com.klewerro.radommap.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@IgnoreExtraProperties
data class InterestPoint(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val url: String? = null,
    val coordinates: @RawValue GeoPoint? = null,
    val categoryId: String? = null
) : Parcelable {



    companion object : Parceler<InterestPoint> {
        override fun InterestPoint.write(parcel: Parcel, flags: Int) {
            if (coordinates?.latitude != null && coordinates.longitude != null) {
                parcel.writeDouble(coordinates.latitude)
                parcel.writeDouble(coordinates.longitude)
            }
        }

        override fun create(parcel: Parcel): InterestPoint = InterestPoint(coordinates = GeoPoint(parcel.readDouble(), parcel.readDouble()))
    }
}