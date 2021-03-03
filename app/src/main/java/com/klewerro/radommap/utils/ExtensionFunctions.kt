package com.klewerro.radommap.utils

import androidx.fragment.app.Fragment
import com.klewerro.radommap.MainActivity

object ExtensionFunctions {

    fun Fragment.setFragmentSubtitle(text: String?) {
        (requireActivity() as MainActivity).supportActionBar?.subtitle = text
    }
}