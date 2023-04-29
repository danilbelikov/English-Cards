package com.example.englishcards.ui.word

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.englishcards.R
import com.example.englishcards.databinding.FragmentWordBinding
import com.example.englishcards.ui.adapters.ListAdapter
import com.example.englishcards.ui.model.Card
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

var numberOfMasteredWords = 14
var numberOfLearnedWords = 14

class WordFragment : Fragment() {
private lateinit var binding: FragmentWordBinding
private lateinit var masteredWords: ArrayList<Card>
private lateinit var learningWords: ArrayList<Card>
private lateinit var repeatingWords: ArrayList<Card>
private lateinit var cardArrayList : ArrayList<Card>
private lateinit var preferences: SharedPreferences
lateinit var word: Array<String>
lateinit var explanation: Array<String>
lateinit var status: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

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
        numberOfMasteredWords = masteredWords.size



        //binding.tvEnglishWord.text = word[0]

    }
    private fun saveData() {

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("gfdg", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(masteredWords)
        numberOfMasteredWords = masteredWords.size
        val json2 = gson.toJson(numberOfMasteredWords)
        numberOfLearnedWords = learningWords.size
        val json3 = gson.toJson(numberOfLearnedWords)

        editor.apply {
            putString("KEY", json)
            putString("KEY3", json2)
            putString("KEY4", json3)
        }.apply()
    }
    private fun loadData() {

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("gfdg", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("KEY", null)


        if (json == null) masteredWords = ArrayList<Card>()

        val type: Type = object : TypeToken<ArrayList<Card>>() {}.getType()

       if (json != null) masteredWords = gson.fromJson(json, type)


        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar_mastered)
        progressBar?.setProgress(masteredWords.size)

    }
    private fun dataInitialize(){
        //Списки со словами

        masteredWords = ArrayList()
        learningWords = ArrayList()
        cardArrayList = ArrayList()
        word = arrayOf(
            "Hello",
            "Yo",
            "Dog",
            "Cat",
            "Moon",
            "Cook",
            "Look",
            "john",
            "Sally",
            "Ligma",
        )
        explanation = arrayOf(
            "Привет\nHello! My name is Danil!",
            "Йо",
            "Собака",
            "Кошка",
            "Луна",
            "Готовить",
            "Смотреть",
            "Джон",
            "Салли",
            "Лигма",
        )
        status = arrayOf(
            "NEW WORD",
            "LEARNING",
            "MASTERED"
        )

        //Вставляем первое слово
        val countRange = (1 until word.size)
        var count = countRange.random()
        loadData()
        if (masteredWords.isEmpty())
        cardArrayList.add(Card(word[count], explanation[count], status[0]))
        else {
            masteredWords[0].status = status[2]
            cardArrayList.add(masteredWords[0])

        }

        //Адаптер
        val adapter = ListAdapter(requireActivity(), cardArrayList)
        binding.listView.adapter = adapter

        //Для передачи на маин активити

        //Сколько раз повторили
        var numberOfRepeatings = 0

        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar_mastered)
        progressBar?.setProgress(masteredWords.size)
        val progressBarLearning = view?.findViewById<ProgressBar>(R.id.progress_bar_learning)


        val explanationTv = view?.findViewById<TextView>(R.id.tvEnglishWordExplanation)
        val watchMeaning = view?.findViewById<TextView>(R.id.watchMeaning)
        val bKnow = view?.findViewById<Button>(R.id.bKnow)
        val bNotKnow = view?.findViewById<Button>(R.id.bNotKnow)
        val cardViewId = view?.findViewById<CardView>(R.id.cardView)

        var progressMastered = 0
        var progressLearning = 0

        progressBar?.max = word.size
        progressBarLearning?.max = word.size

        //if (numberOfMasteredWords == 76) saveData()



//такксссс



adapter.onItemClick = { any: Any, view: View ->
    var randomNumber = (0..6).random()


    if (any == 1) {


        if (cardArrayList[0].status == status[2]){
            cardArrayList[0].status = status[0]
            masteredWords.remove(cardArrayList[0])
        }
        //todo если ТЕКУЩЕЕ слово содержится в списке ИЗУЧЕНИЯ тогда удаляем
        if (learningWords.contains(cardArrayList[0])) {
            numberOfRepeatings += 1
            Log.d("sda", "numberofRepeatings: $numberOfRepeatings")
            if (numberOfRepeatings == 5) {
                learningWords.remove(cardArrayList[0])
                numberOfRepeatings = 0
            }

        }
        if (masteredWords.contains(cardArrayList[0])) masteredWords.remove(cardArrayList[0])
        if (cardArrayList[0].status != status[1]) {
            cardArrayList[0].status = status[0]
            masteredWords.add(cardArrayList[0])
        }

        //todo очищаем текущую карточку, чтобы показать новое слово
        cardArrayList.clear()
        //todo проверяю, если предыдущее слово было из learningWords, тогда удаляю его (так как мы выучили)

        //todo устанавливаем каунт для дальнейшего выбора слова
        count += 1
        if (count >= word.size) count = 0

        var nextWord = Card(word[count], explanation[count], status[0])
        //todo выбрали слово, но нужно сделать несколько проверок


        //todo на рандоме может выпасть слово для повторения
        if (randomNumber == 1) {
            if(learningWords.isNotEmpty()) {
                var positionLearning = (0 until learningWords.size).random()
                nextWord = learningWords[positionLearning]
                //learningWords.removeAt(positionLearning)
            }
        }

        //todo если изученные содержат уже такое слово, тогда оно будет с приставкой MASTERED
        if (learningWords.contains(Card(word[count],explanation[count], status[1]))) nextWord = Card(word[count],explanation[count], status[1])
        if (masteredWords.contains(nextWord)) nextWord = Card(word[count], explanation[count], status[2])
        cardArrayList.add(nextWord)

        progressLearning = learningWords.size
        progressBarLearning?.setProgress(progressLearning)

        progressMastered = masteredWords.size
        progressBar?.setProgress(progressMastered)
        progressBar?.visibility = View.GONE

        Log.d("dsa", "masterSIZE == ${masteredWords.size}")
        Log.d("dsa", "masterSIZE == $masteredWords")
        saveData()
        TransitionManager.beginDelayedTransition(binding.listView, Slide())
        adapter.notifyDataSetChanged()

    }
    //todo разобраться со второй кнопкой

    if (any == 2) {
        Toast.makeText(requireContext(),"$any", Toast.LENGTH_SHORT).show()
        if (cardArrayList[0].status == status[2]){
            cardArrayList[0].status = status[0]
            masteredWords.remove(cardArrayList[0])
        }
        if (cardArrayList[0].status == status[2]) masteredWords.remove(cardArrayList[0])
        Log.d("dsa", "masterSIZE == ${masteredWords.size}")
        Log.d("dsa","learningWordsStart: $learningWords")
        count += 1
        if (count >= word.size) count = 0
        Log.d("dsa","count: $count")
        var nextWord = Card(word[count], explanation[count], status[0])
        if (nextWord == Card(word[count], explanation[count], status[0])
            && learningWords.contains(Card(word[count], explanation[count], status[1]))) nextWord = Card(word[count], explanation[count], status[1])
        Log.d("dsa","nextWord1: $nextWord")

        if (learningWords.contains(cardArrayList[0])) {
            learningWords.remove(cardArrayList[0])
        }
        Log.d("dsa","learnWordsRemove: $learningWords")

        cardArrayList[0].status = status[1]
        learningWords.add(cardArrayList[0])
        Log.d("dsa","learningAdd: $learningWords")

        cardArrayList.clear()


        if (randomNumber == 1 && learningWords.size > 4) {
            if(learningWords.isNotEmpty()) {
                var positionLearning = (0 until learningWords.size).random()
                nextWord = learningWords[positionLearning]
                count -= 1
                Log.d("dsa","learningRANDOM!: $learningWords")

            }
        }
        if (masteredWords.contains(nextWord)) nextWord = Card(word[count], explanation[count], status[2])
        Log.d("dsa","nextWordFINAL: $nextWord")
        cardArrayList.add(nextWord)
        Log.d("dsa","learningWordsFINAL: $learningWords")


        progressLearning = learningWords.size
        progressBarLearning?.setProgress(progressLearning)

        progressMastered = masteredWords.size
        progressBar?.setProgress(progressMastered)

        saveData()
        TransitionManager.beginDelayedTransition(binding.listView, Slide())
        adapter.notifyDataSetChanged()
    }
}

    }


companion object{

}
}
