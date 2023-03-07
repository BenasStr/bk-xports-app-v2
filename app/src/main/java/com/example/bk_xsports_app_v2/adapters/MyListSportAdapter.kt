package com.example.bk_xsports_app_v2.adapters

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.util.Log
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
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.Sport
import com.example.bk_xsports_app_v2.network.data.SportData
import com.example.bk_xsports_app_v2.ui.main.myList.MyListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyListSportAdapter(private val navController: NavController, private var sportData: MutableList<Sport>, private val token: String, private val context: Context):
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
            val sport = sportData[position]
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

            holder.itemView.setOnClickListener {
                val action = MyListFragmentDirections.actionNavigationMyListToMyListCategoryFragment(sport.id)
                navController.navigate(action)
            }

            holder.itemView.setOnLongClickListener {
                createDialogBox(sport, position)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return sportData.size.plus(1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) VIEW_TYPE_ADD_ITEM else VIEW_TYPE_ITEM
    }

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)
        val gradient: ImageView = view.findViewById(R.id.item_gradient)
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

    private fun createDialogBox(sport: Sport, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Remove sport from my list?")
        builder.setMessage("Do you want to remove ${sport.name.lowercase(Locale.ROOT)} from your list?")
        builder.setPositiveButton("Remove") { dialog, which ->
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Api.retrofitService.deleteMyListSport(token, sport.id)
                    sportData.removeAt(position)
                    notifyItemRemoved(position)
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Api request failed", e)
                }
            }
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }
}