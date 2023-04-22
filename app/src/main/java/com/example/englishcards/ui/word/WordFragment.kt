package com.example.englishcards.ui.word

import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionManager
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


class WordFragment : Fragment() {
private lateinit var binding: FragmentWordBinding
private lateinit var knownWords: ArrayList<String>
private lateinit var learningWords: ArrayList<String>
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
        knownWords = arrayListOf<String>()
        word = arrayOf(
            getString(R.string.hello),
            "Yo"
        )
        explanation = arrayOf(
            getString(R.string.привет),
            "trans\nhgfh\ngfhgfhfg\n"
        )
        status = arrayOf(
            "NEW WORD",
            "LEARNING"
        )
        learningWords = ArrayList()
        cardArrayList = ArrayList()
        var adapter = ListAdapter(requireActivity(), cardArrayList)
        cardArrayList.add(Card(word[0], explanation[0], status[0]))
        binding.listView.adapter = adapter
        var count = 1


        val buttonKnow : Button?= view?.findViewById(R.id.bKnow)
        buttonKnow?.setOnClickListener {
            adapter.onClick(buttonKnow)
        }
        val buttonNotKnow : Button?= view?.findViewById(R.id.bNotKnow)

adapter.onItemClick = { any: Any, view: View ->
    if (any == 1) {
        cardArrayList.clear()
        cardArrayList.add(Card(word[count], explanation[count], status[0]))
        TransitionManager.beginDelayedTransition(binding.listView, Explode())
        count += 1
        if (count >= word.size) count = 0
        adapter.notifyDataSetChanged()
    }
    if (any == 2) Toast.makeText(requireContext(),"gfdgdf",Toast.LENGTH_SHORT).show()
    // сделать разные анимации при переходе
}
        binding.listView.setOnItemClickListener { parent, view, position, id ->

            /*cardArrayList.clear()
            //adapter.onClick(view)
            cardArrayList.add(Card(word[count], explanation[count], status[0]))
            TransitionManager.beginDelayedTransition(binding.listView, Explode())
            count += 1
            if (count >= word.size) count = 0
            adapter.notifyDataSetChanged()*/
        }

    }


companion object{

}
}