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
import com.example.bk_xsports_app_v2.ui.main.explore.ExploreFragmentDirections

class SportAdapter(private val navController: NavController, private val sportData: SportData):
    RecyclerView.Adapter<SportAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.sport_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val sport = sportData.data[position]
        holder.textView.text = sport.name
//        holder.imageView.setImageResource()
    }

    override fun getItemCount() = sportData.data.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)

        init {
            itemView.setOnClickListener {
                val sport = sportData.data[adapterPosition]
                val action = ExploreFragmentDirections.actionNavigationExploreToCategoryFragment(sport.id)
                navController.navigate(action)
            }
        }
    }
}