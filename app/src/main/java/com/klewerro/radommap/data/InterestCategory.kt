package com.klewerro.radommap.data

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class InterestCategory(
    val id: String? = null,
    val name: String? = null
)