package com.example.englishcards.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishcards.R
import com.example.englishcards.ui.model.CardsIntro

class CardsAdapter(private val cardsList: ArrayList<CardsIntro>) :
    RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        return CardsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val currentItem = cardsList[position]
        holder.tvHeading.text = currentItem.name
        holder.tvProgress.text = currentItem.progress
    }

    class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val tvProgress: TextView = itemView.findViewById(R.id.tvProgress)
    }
}