package com.example.englishcards.ui.adapters

import android.app.Activity
import android.content.Context
import android.transition.AutoTransition
import android.transition.ChangeTransform
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.englishcards.R
import com.example.englishcards.databinding.ItemWordsBinding
import com.example.englishcards.databinding.ListItemBinding
import com.example.englishcards.ui.model.Card

class ListAdapter(private val context: Activity, private val arrayList: ArrayList<Card>) : ArrayAdapter<Card>(context, R.layout.list_item, arrayList), View.OnClickListener {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        /*val inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)*/

        val binding = convertView?.tag as ListItemBinding? ?:
        createBinding(parent.context)

        binding.tvEnglishWord.text = arrayList[position].word
        binding.tvEnglishWordExplanation.text = arrayList[position].explanation

        /*val wordView : TextView = view.findViewById(R.id.tvEnglishWord)
        val explanationView : TextView = view.findViewById(R.id.tvEnglishWordExplanation)
        val statusView : TextView = view.findViewById(R.id.tvWordStatus)
        wordView.text = arrayList[position].word
        explanationView.text = arrayList[position].explanation
        statusView.text = arrayList[position].status*/
        return binding.root
    }

    override fun onClick(v: View?) {
            //if (v?.isVisible == true) v.visibility = View.VISIBLE
    }

    private fun createBinding(context: Context) :ListItemBinding{
        val binding = ListItemBinding.inflate(LayoutInflater.from(context))
        binding.watchMeaning.setOnClickListener{

            val explanation = binding.tvEnglishWordExplanation
            if (explanation.isVisible) {
                TransitionManager.beginDelayedTransition(binding.cardViewWord, Explode())
                explanation.visibility = View.GONE

            }
            else {
                TransitionManager.beginDelayedTransition(binding.cardViewWord, Slide())
                explanation.visibility = View.VISIBLE
            }

        }
        return binding
    }

}