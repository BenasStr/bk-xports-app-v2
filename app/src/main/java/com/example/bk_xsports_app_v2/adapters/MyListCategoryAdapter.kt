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
import com.example.bk_xsports_app_v2.network.data.CategoryData
import com.example.bk_xsports_app_v2.ui.main.myList.MyListCategoryFragmentDirections

class MyListCategoryAdapter(
        private val navController: NavController,
        private val categoryData: CategoryData,
        private val token: String,
        private val context: Context):
    RecyclerView.Adapter<MyListCategoryAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val category = categoryData.data[position]
        holder.textView.text = category.name

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 16f
        circularProgressDrawable.centerRadius = 100f
        circularProgressDrawable.start()

        holder.imageView.load(category.photo) {
            addHeader("Authorization", token)
            placeholder(circularProgressDrawable)
            error(R.drawable.ic_baseline_hide_image_36)
            diskCachePolicy(CachePolicy.ENABLED)
        }
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