package com.example.bk_xsports_app_v2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.SportData

class SportAdapter(private val navController: NavController, private val sportData: SportData):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_ADD_ITEM) {
            val view = inflater.inflate(R.layout.add_sport_item, parent, false)
            AddItemViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.sport_item, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val sport = sportData.data[position]
            holder.textView.text = sport.name
        }
    }

    override fun getItemCount(): Int {
        return sportData.data.size.plus(1)
    }

    //done
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_ADD_ITEM else VIEW_TYPE_ITEM
    }

    //done
    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)
    }


    //done
    private inner class AddItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Add any click listeners or other methods here
    }

    //done
    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_ADD_ITEM = 1
    }
}