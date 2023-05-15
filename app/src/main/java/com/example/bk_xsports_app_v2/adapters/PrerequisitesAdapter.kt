package com.example.bk_xsports_app_v2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.Trick
import com.example.bk_xsports_app_v2.util.Status

class PrerequisitesAdapter(private val trickData: List<Trick>):
    RecyclerView.Adapter<PrerequisitesAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.prerequisite_item, parent, false)
        return ItemViewHolder(adapterLayout);
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trick = trickData[position]
        holder.trickPrerequisite.text = trick.name
        colorTextBasedOnStatus(trick, holder.trickPrerequisite)
    }

    override fun getItemCount() = trickData.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val trickPrerequisite: TextView = view.findViewById(R.id.prerequisite_text)
    }

    private fun colorTextBasedOnStatus(trick: Trick, textView: TextView) {
        when (trick.status) {
            Status.PLANNING.status -> {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.orange_200))
            }
            Status.STARTED.status -> {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.yellow_200))
            }
            Status.DONE.status -> {
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green_500))
            }
        }
    }
}