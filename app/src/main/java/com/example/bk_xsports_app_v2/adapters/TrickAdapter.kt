package com.example.bk_xsports_app_v2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.Trick
import com.example.bk_xsports_app_v2.network.data.TrickData
import com.example.bk_xsports_app_v2.ui.main.myList.MyListCategoryFragmentDirections
import com.example.bk_xsports_app_v2.ui.main.trick.TrickListFragmentDirections
import com.example.bk_xsports_app_v2.util.Status

class TrickAdapter(private val navController: NavController, private val trickData: MutableList<Trick>):
    RecyclerView.Adapter<TrickAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.trick_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trick = trickData[position]
        holder.trickName.text = trick.name
        holder.description.text = trick.description
        holder.difficulty.text = trick.difficulty

        setItemBackgroundColor(trick, holder)
    }

    override fun getItemCount() = trickData.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val trickName: TextView = view.findViewById(R.id.trick_name)
        val description: TextView = view.findViewById(R.id.short_description)
        val difficulty: TextView = view.findViewById(R.id.difficulty_text)

        init {
            itemView.setOnClickListener {
                val trick = trickData[adapterPosition]
                val action = TrickListFragmentDirections.actionTrickFragmentToTrickFragment2(trick.id)
                navController.navigate(action)
            }
        }
    }

    private fun setItemBackgroundColor(trick: Trick, holder: ItemViewHolder) {
        when (trick.status) {
            Status.PLANNING.status -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red_200))
            }
            Status.STARTED.status -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.teal_200))
            }
            Status.DONE.status -> {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green_700))
            }
        }
    }
}