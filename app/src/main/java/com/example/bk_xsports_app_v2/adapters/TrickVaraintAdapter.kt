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
import com.example.bk_xsports_app_v2.ui.main.trick.TrickFragmentDirections
import com.example.bk_xsports_app_v2.util.Status
import com.google.android.material.card.MaterialCardView

class TrickVaraintAdapter(private val navController: NavController, private val trickData: List<Trick>, private val sportId: Int, private val categoryId: Int):
    RecyclerView.Adapter<TrickVaraintAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrickVaraintAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.trick_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TrickVaraintAdapter.ItemViewHolder, position: Int) {
        val trick = trickData[position]
        holder.trickName.text = trick.name
        holder.description.text = trick.shortDescription
        holder.difficulty.text = trick.difficulty
        holder.learnedCount.text = ""

        setItemBackgroundColor(trick, holder.cardBackground)
    }

    override fun getItemCount() = trickData.size

    inner class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val trickName: TextView = view.findViewById(R.id.trick_name)
        val description: TextView = view.findViewById(R.id.short_description)
        val difficulty: TextView = view.findViewById(R.id.difficulty_text)
        val learnedCount: TextView = view.findViewById(R.id.learned_count)
        val cardBackground: MaterialCardView = view.findViewById(R.id.trick_card)

        init {
            itemView.setOnClickListener {
                val trick = trickData[adapterPosition]
                val action = TrickFragmentDirections.actionTrickFragment2Self(trick.id, sportId, categoryId, true)
                navController.navigate(action)
            }
        }
    }

    private fun setItemBackgroundColor(trick: Trick, cardView: MaterialCardView) {
        when (trick.status) {
            Status.PLANNING.status -> {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.orange_200))
            }
            Status.STARTED.status -> {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow_200))
            }
            Status.DONE.status -> {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green_500))
            }
        }
    }
}