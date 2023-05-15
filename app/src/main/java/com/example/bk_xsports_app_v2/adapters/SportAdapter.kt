package com.example.bk_xsports_app_v2.adapters

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.Sport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SportAdapter(private val sportData: MutableList<Sport>, private val token: String, private val context: Context) :
    RecyclerView.Adapter<SportAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.sport_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SportAdapter.ItemViewHolder, position: Int) {
        val sport = sportData[position]
        holder.textView.text = sport.name

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 16f
        circularProgressDrawable.centerRadius = 100f
        circularProgressDrawable.start()

        holder.imageView.load(sport.photo?.replace("localhost", "192.168.1.219")) {
            addHeader("Authorization", token)
            placeholder(circularProgressDrawable)
            error(R.drawable.ic_baseline_hide_image_26)
            diskCachePolicy(CachePolicy.ENABLED)
        }

        holder.gradient.alpha = 1f

        holder.sportCard.setOnClickListener {
            createDialogBox(sport, position)
        }
    }

    override fun getItemCount() = sportData.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_text)
        val gradient: ImageView = view.findViewById(R.id.item_gradient)
        val sportCard: CardView = view.findViewById(R.id.sport_item_card)
    }

    private fun createDialogBox(sport: Sport, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add sport to my list?")
        builder.setMessage("Do you want to add ${sport.name} to your list?")
        builder.setPositiveButton("Add") { dialog, which ->
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Api.retrofitService.addMyListSport(token, sport.id)
                    sportData.removeAt(position)
                    notifyItemRemoved(position)
                } catch (e: Exception) {
                    Log.e(TAG, "Api request failed", e)
                }
            }
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }
}