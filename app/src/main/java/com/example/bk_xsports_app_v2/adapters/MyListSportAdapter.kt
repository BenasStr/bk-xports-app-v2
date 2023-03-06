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
    RecyclerView.Adapter<MyListSportAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.sport_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
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

    override fun getItemCount() = sportData.data.size

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
}