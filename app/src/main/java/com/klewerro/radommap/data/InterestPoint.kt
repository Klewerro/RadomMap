package com.klewerro.radommap.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterestPoint(
    val id: Int,
    val name: String,
    val description: String,
    val url: String,
    val categoryId: Int
) : Parcelable