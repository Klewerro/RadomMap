package com.klewerro.radommap.utils

import android.content.res.Resources.getSystem

object MetricsUtil {

    fun Int.convertPixelsToDp(): Int = (this / getSystem().displayMetrics.density).toInt()

    fun Int.convertPixelsToPx(): Int  = (this * getSystem().displayMetrics.density).toInt()

}