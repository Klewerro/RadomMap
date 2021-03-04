package com.klewerro.radommap.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import com.klewerro.radommap.MainActivity

object ExtensionFunctions {

    fun Fragment.setFragmentSubtitle(text: String?) {
        (requireActivity() as MainActivity).supportActionBar?.subtitle = text
    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }
}