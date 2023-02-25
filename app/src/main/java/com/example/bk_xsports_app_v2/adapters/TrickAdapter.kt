package com.example.bk_xsports_app_v2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.TrickData

class TrickAdapter(private val trickData: TrickData):
    RecyclerView.Adapter<TrickAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.trick_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trick = trickData.data[position]
        println(trick.name)
        println(trick.description)
        holder.trickName.text = trick.name
        holder.description.text = trick.description
    }

    override fun getItemCount() = trickData.data.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val trickName: TextView = view.findViewById(R.id.trick_name)
        val description: TextView = view.findViewById(R.id.short_description)
    }

}