package com.example.englishcards.ui.main

import android.content.Intent
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishcards.R
import com.example.englishcards.databinding.FragmentMainBinding
import com.example.englishcards.ui.adapters.CardsAdapter
import com.example.englishcards.ui.contract.navigator
import com.example.englishcards.ui.model.CardsIntro
import com.example.englishcards.ui.word.WordFragment

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardsArrayList: ArrayList<CardsIntro>
    lateinit var heading: Array<String>
    lateinit var progress: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        var adapter = CardsAdapter(cardsArrayList)
        recyclerView.adapter = adapter
        val button = view?.findViewById<Button>(R.id.buttonPractice)
        button?.setOnClickListener {
            navigator().goToCards1()
        }
        adapter.setOnItemClickListener(object : CardsAdapter.OnItemClickListener{


            override fun onItemClick(position: Int) {
                if (position == 0) {
                    navigator().goToCards1()
                }
                if (position == 1) {
                    navigator().goToCards2()
                }
                if (position == 2) {
                    navigator().goToCards3()
                }

            }
        })
    }


    private fun dataInitialize() {
        cardsArrayList = arrayListOf<CardsIntro>()

        heading = arrayOf(
            getString(R.string.common_words),
            getString(R.string.Basic_words),
            getString(R.string.hard_words)
        )
        progress = arrayOf(
            getString(R.string.progress1),
            getString(R.string.progress2),
            getString(R.string.progress3)
        )
        for (i in heading.indices) {
            val cards = CardsIntro(heading[i], progress[i])
            cardsArrayList.add(cards)
        }



    }

}

