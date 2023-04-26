package com.example.englishcards.ui.word

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
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

        //Для передачи на маин активити
        numberOfMasteredWords = masteredWords.size
        //Сколько раз повторили
        var numberOfRepeatings = 0

        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar_mastered)
        val progressBarLearning = view?.findViewById<ProgressBar>(R.id.progress_bar_learning)
        var progressMastered = 0
        var progressLearning = 0

        progressBar?.max = word.size
        progressBarLearning?.max = word.size

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

        Log.d("dsa", "masterSIZE == ${masteredWords.size}")
        Log.d("dsa", "masterSIZE == $masteredWords")
        TransitionManager.beginDelayedTransition(binding.listView, Explode())
        adapter.notifyDataSetChanged()
    }
    //todo разобраться со второй кнопкой

    if (any == 2) {
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