package com.example.bk_xsports_app_v2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.data.CategoryData
import com.example.bk_xsports_app_v2.ui.main.myList.MyListCategoryFragmentDirections

class MyListCategoryAdapter(private val navController: NavController, private val categoryData: CategoryData):
    RecyclerView.Adapter<MyListCategoryAdapter.ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            return ItemViewHolder(adapterLayout)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val category = categoryData.data[position]
            holder.textView.text = category.name
//        holder.imageView.setImageResource()
        }

        override fun getItemCount() = categoryData.data.size

        inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.item_image)
            val textView: TextView = view.findViewById(R.id.item_text)

            init {
                itemView.setOnClickListener {
                    val category = categoryData.data[adapterPosition]
                    val action = MyListCategoryFragmentDirections.actionMyListCategoryFragmentToTrickFragment(category.sportId, category.id)
                    navController.navigate(action)
                }
            }
        }
    }