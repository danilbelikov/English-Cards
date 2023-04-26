package com.example.englishcards.ui.adapters

import android.app.Activity
import android.content.Context
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.isVisible
import com.example.englishcards.R
import com.example.englishcards.databinding.ListItemBinding
import com.example.englishcards.ui.model.Card

class ListAdapter(private val context: Activity, private val arrayList: ArrayList<Card>) : ArrayAdapter<Card>(context, R.layout.list_item, arrayList), View.OnClickListener {
    var onItemClick: ((pos: Any,view: View) -> Unit)? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*val inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)*/

        val binding = convertView?.tag as ListItemBinding? ?:
        createBinding(parent.context)

        binding.tvEnglishWord.text = arrayList[position].word
        binding.tvEnglishWordExplanation.text = arrayList[position].explanation
        binding.tvWordStatus.text = arrayList[position].status
        binding.bKnow.setTag(1)
        binding.bNotKnow.setTag(2)

        if (binding.tvWordStatus.text == "LEARNING") binding.tvWordStatus.setBackgroundResource(R.drawable.rounded_corner_learning)
        if (binding.tvWordStatus.text == "MASTERED") binding.tvWordStatus.setBackgroundResource(R.drawable.rounded_corner_mastered)


        return binding.root
    }

    override fun onClick(v: View) {
val dfdf = v.getTag()
onItemClick?.invoke(dfdf,v)
    }


    private fun createBinding(context: Context) :ListItemBinding{
        val binding = ListItemBinding.inflate(LayoutInflater.from(context))
binding.bKnow.setOnClickListener(this)
binding.bNotKnow.setOnClickListener(this)


        binding.watchMeaning.setOnClickListener{

            val explanation = binding.tvEnglishWordExplanation
            if (explanation.isVisible) {
                TransitionManager.beginDelayedTransition(binding.cardViewWord, Explode())
                explanation.visibility = View.GONE
                binding.bKnow.visibility = View.GONE
                binding.bNotKnow.visibility = View.GONE


            }
            else {
                TransitionManager.beginDelayedTransition(binding.cardViewWord, Slide())
                explanation.visibility = View.VISIBLE
                binding.bKnow.visibility =View.VISIBLE
                binding.bNotKnow.visibility =View.VISIBLE
            }

        }
        return binding
    }


}