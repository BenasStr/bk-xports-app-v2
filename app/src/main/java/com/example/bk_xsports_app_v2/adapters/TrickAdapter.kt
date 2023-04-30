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
import com.example.bk_xsports_app_v2.network.data.TrickExtended
import com.example.bk_xsports_app_v2.ui.main.trick.TrickListFragmentDirections
import com.example.bk_xsports_app_v2.util.Status
import com.google.android.material.card.MaterialCardView

class TrickAdapter(private val navController: NavController, private val trickData: MutableList<TrickExtended>, private val sportId: Int, private val categoryId: Int):
    RecyclerView.Adapter<TrickAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.trick_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trick = trickData[position]
        holder.trickName.text = trick.name
        holder.description.text = trick.shortDescription
        holder.difficulty.text = trick.difficulty
        holder.learnedCount.text = getLearnedCount(trick)

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
                val action = TrickListFragmentDirections.actionTrickFragmentToTrickFragment2(trick.id, sportId, categoryId, false)
                navController.navigate(action)
            }
        }
    }

    private fun setItemBackgroundColor(trick: TrickExtended, cardView: MaterialCardView) {
        var done = false

        val countAll = 1 + trick.trickVariants.size

        var count = trick.trickVariants.stream()
            .filter { variant -> variant.status == "Done"
            }.count()
            .toInt()

        if (trick.status == "Done") {
            count += 1
        }

        if (countAll == count) {
            done = true
        }

        when (done) {
            false -> {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow_200))
            }
            true -> {
                cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green_500))
            }
        }
    }

    private fun getLearnedCount(trick: TrickExtended): String {
        val countAll = 1 + trick.trickVariants.size

        var count = trick.trickVariants.stream()
            .filter {
                variant -> variant.status == "Done"
            }.count()

        if (trick.status == "Done") {
            count += 1
        }

        return "learned ${count}/${countAll}"
    }
}