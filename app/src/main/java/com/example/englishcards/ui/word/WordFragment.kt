package com.example.englishcards.ui.word

import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.englishcards.R
import com.example.englishcards.databinding.FragmentWordBinding
import com.example.englishcards.ui.adapters.ListAdapter
import com.example.englishcards.ui.model.Card

var numberOfMasteredWords = 76
class WordFragment : Fragment() {
private lateinit var binding: FragmentWordBinding
private lateinit var masteredWords: ArrayList<Card>
private lateinit var learningWords: ArrayList<Card>
private lateinit var repeatingWords: ArrayList<Card>
private lateinit var cardArrayList : ArrayList<Card>
lateinit var word: Array<String>
lateinit var explanation: Array<String>
lateinit var status: Array<String>


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



        //binding.tvEnglishWord.text = word[0]

    }
    private fun dataInitialize(){
        //Списки со словами
        masteredWords = ArrayList()
        learningWords = ArrayList()
        cardArrayList = ArrayList()
        word = arrayOf(
            "Hello1",
            "Yo2",
            "Dog3",
            "Cat4",
            "Moon5",
            "Cook6",
            "Lok7",
            "john8",
            "Sally9",
            "Ligma10",
        )
        explanation = arrayOf(
            getString(R.string.привет),
            "Йо",
            "Собака",
            "Кошка",
            "Луна",
            "Готовить",
            "Смотреть",
            "Смотреть",
            "Смотреть",
            "Смотреть",
        )
        status = arrayOf(
            "NEW WORD",
            "LEARNING",
            "MASTERED"
        )
        //Вставляем первое слово
        val countRange = (1 until word.size)
        var count = countRange.random()
        cardArrayList.add(Card(word[count], explanation[count], status[0]))

        //Адаптер
        val adapter = ListAdapter(requireActivity(), cardArrayList)
        binding.listView.adapter = adapter

        //masteredWords.clear()
        numberOfMasteredWords = masteredWords.size
        var numberOfRepeatings = 0

adapter.onItemClick = { any: Any, view: View ->
    var randomNumber = (0..6).random()



    if (any == 1) {

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

        /*when {
            count != 0 -> {
                masteredWords.add(Card(word[count -1], explanation[count -1], status[0]))
            }
            else -> {
                masteredWords.add(Card(word[word.size -1], explanation[word.size -1], status[0]))
            }
        }*/

        var nextWord = Card(word[count], explanation[count], status[0])
        //todo выбрали слово, но нужно сделать несколько проверок



        /*if (randomNumber != 1 && randomNumber != 2) {
            masteredWords.add(nextWord)
        }*/

        //todo на рандоме может выпасть слово для повторения
        if (randomNumber == 1) {
            if(learningWords.isNotEmpty()) {
                var positionLearning = (0 until learningWords.size).random()
                nextWord = learningWords[positionLearning]
                //learningWords.removeAt(positionLearning)
            }
        }
        /*if (randomNumber == 2) {
            if (masteredWords.isNotEmpty()) {
                var positionMastered = (0 until masteredWords.size).random()
                nextWord = masteredWords[positionMastered]
            }
        }*/

        //todo если изученные содержат уже такое слово, тогда оно будет с приставкой MASTERED
        if (learningWords.contains(Card(word[count],explanation[count], status[1]))) nextWord = Card(word[count],explanation[count], status[1])
        if (masteredWords.contains(nextWord)) nextWord = Card(word[count], explanation[count], status[2])
        cardArrayList.add(nextWord)



        TransitionManager.beginDelayedTransition(binding.listView, Explode())
        adapter.notifyDataSetChanged()
    }
    //todo разобраться со второй кнопкой

    if (any == 2) {
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

        //добавляет не то, если второй раз нижмаю
        /*if (count != 0)  learningWords.add(Card(word[count-1],explanation[count-1], status[1]))
        else learningWords.add(Card(word[word.size -1], explanation[word.size -1], status[1]))*/
        //todo добавляем не то
        cardArrayList[0].status = status[1]
        learningWords.add(cardArrayList[0])
        Log.d("dsa","learningAdd: $learningWords")
        //learningWords.add(cardArrayList[0])
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






        TransitionManager.beginDelayedTransition(binding.listView, Explode())
        adapter.notifyDataSetChanged()
    }
    // сделать разные анимации при переходе
}
        /*binding.listView.setOnItemClickListener { parent, view, position, id ->

            *//*cardArrayList.clear()
            //adapter.onClick(view)
            cardArrayList.add(Card(word[count], explanation[count], status[0]))
            TransitionManager.beginDelayedTransition(binding.listView, Explode())
            count += 1
            if (count >= word.size) count = 0
            adapter.notifyDataSetChanged()*//*
        }*/

    }


companion object{

}
}