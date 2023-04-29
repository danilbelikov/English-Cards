package com.example.englishcards.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishcards.R
import com.example.englishcards.databinding.FragmentMainBinding
import com.example.englishcards.ui.adapters.CardsAdapter
import com.example.englishcards.ui.contract.navigator
import com.example.englishcards.ui.model.Card
import com.example.englishcards.ui.model.CardsIntro
import com.example.englishcards.ui.word.WordFragment
import com.example.englishcards.ui.word.numberOfLearnedWords

import com.example.englishcards.ui.word.numberOfMasteredWords
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardsArrayList: ArrayList<CardsIntro>
    private lateinit var progressInt: ArrayList<Int>
    lateinit var heading: Array<String>
    lateinit var progress: Array<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        dataInitialize()


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        var adapter = CardsAdapter(cardsArrayList)
        recyclerView.adapter = adapter
        val button = view?.findViewById<Button>(R.id.buttonPractice)

        //saveData()

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
        progressInt = arrayListOf(numberOfMasteredWords)



        heading = arrayOf(
            getString(R.string.common_words),
            getString(R.string.Basic_words),
            getString(R.string.hard_words)
        )
        progress = arrayOf(
            "${numberOfMasteredWords} слов из 10 изучено",
            getString(R.string.progress2),
            getString(R.string.progress3)
        )
        for (i in heading.indices) {
            val cards = CardsIntro(heading[i], progress[i])
            cardsArrayList.add(cards)
        }
    }

   /* private fun saveData() {

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("main_fragment", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(progressInt)

        editor.apply {
            putString("KEY2", json)

        }.apply()
    }*/
    private fun loadData() {

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("gfdg", Context.MODE_PRIVATE)
        val gson = Gson()
       // val json = sharedPreferences.getString("KEY2", "0")
        val json2 = sharedPreferences.getString("KEY3", "0")
        val json3 = sharedPreferences.getString("KEY4", "0")
        val type2: Type = object : TypeToken<Int>() {}.getType()
        numberOfMasteredWords = gson.fromJson(json2, type2)
       numberOfLearnedWords = gson.fromJson(json3,type2)
        val type: Type = object : TypeToken<ArrayList<Int>>() {}.getType()

        //progressInt = gson.fromJson(json, type)




    }

}

