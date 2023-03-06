package com.example.bk_xsports_app_v2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.SportData
import com.example.bk_xsports_app_v2.ui.main.myList.MyListFragmentDirections

class MyListSportAdapter(private val navController: NavController, private val sportData: SportData, private val token: String, private val context: Context):
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

            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 16f
            circularProgressDrawable.centerRadius = 100f
            circularProgressDrawable.start()

            holder.imageView.load(sport.photo) {
                addHeader("Authorization", token)
                placeholder(circularProgressDrawable)
                error(R.drawable.ic_baseline_hide_image_26)
                diskCachePolicy(CachePolicy.ENABLED)
            }

            holder.gradient.alpha = 1f
        }
    }

    override fun getItemCount(): Int {
        return sportData.data.size.plus(1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_ADD_ITEM else VIEW_TYPE_ITEM
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)
        val gradient: ImageView = view.findViewById(R.id.item_gradient)

        init {
            itemView.setOnClickListener {
                val sport = sportData.data[adapterPosition]
                val action = MyListFragmentDirections.actionNavigationMyListToMyListCategoryFragment(sport.id)
                navController.navigate(action)
            }
        }
    }

    private inner class AddItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                val action = MyListFragmentDirections.actionNavigationMyListToNavigationExplore()
                navController.navigate(action)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_ADD_ITEM = 1
    }
}