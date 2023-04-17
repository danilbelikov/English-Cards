package com.example.englishcards.ui.adapters

import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.englishcards.R
import com.example.englishcards.ui.model.CardsIntro
import com.example.englishcards.ui.word.WordFragment

class CardsAdapter(private val cardsList: ArrayList<CardsIntro>) :
    RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {
    private lateinit var mListener : OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener (listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        return CardsViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {

        val currentItem = cardsList[position]
        holder.tvHeading.text = currentItem.name
        holder.tvProgress.text = currentItem.progress
    }



    class CardsViewHolder(itemView: View, listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val tvProgress: TextView = itemView.findViewById(R.id.tvProgress)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}