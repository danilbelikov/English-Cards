package com.example.englishcards.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.englishcards.R
import com.example.englishcards.databinding.FragmentMainBinding
import com.example.englishcards.databinding.FragmentWordBinding


class WordFragment : Fragment() {
private lateinit var binding: FragmentWordBinding
lateinit var word: Array<String>
lateinit var translation: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        binding.tvEnglishWord.text = word[0]

    }
    private fun dataInitialize(){
        word = arrayOf(
            getString(R.string.hello)
        )
        translation = arrayOf(
            getString(R.string.привет)
        )
    }
companion object{

}
}