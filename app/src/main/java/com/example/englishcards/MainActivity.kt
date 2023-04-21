package com.example.englishcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.englishcards.ui.contract.Navigator
import com.example.englishcards.ui.main.MainFragment
import com.example.englishcards.ui.word.Word2Fragment
import com.example.englishcards.ui.word.Word3Fragment
import com.example.englishcards.ui.word.WordFragment

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MainFragment())


    }

    private fun replaceFragment(mainFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, mainFragment)
        fragmentTransaction.commit()
    }


    private fun launchFragment (fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(com.google.android.material.R.anim.abc_fade_in, com.google.android.material.R.anim.abc_fade_out)
            .addToBackStack(null)
            .replace(R.id.frame_layout,fragment)
            .commit()
    }

    override fun goToCards1() {
        launchFragment(WordFragment())
    }

    override fun goToCards2() {
        launchFragment(Word2Fragment())
    }

    override fun goToCards3() {
        launchFragment(Word3Fragment())
    }
}