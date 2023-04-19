package com.example.englishcards.ui.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator() : Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun goToCards1()
    fun goToCards2()
    fun goToCards3()
}