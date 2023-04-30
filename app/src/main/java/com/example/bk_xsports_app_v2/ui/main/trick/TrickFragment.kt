package com.example.bk_xsports_app_v2.ui.main.trick

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.PrerequisitesAdapter
import com.example.bk_xsports_app_v2.adapters.TrickVaraintAdapter
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.model.TrickMainViewModel
import com.example.bk_xsports_app_v2.network.data.TrickExtended
import com.example.bk_xsports_app_v2.network.data.TrickMainData
import com.example.bk_xsports_app_v2.util.Status
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_trick.view.*

class TrickFragment : Fragment() {

    private val args: TrickFragmentArgs by navArgs()
    private val trickMainViewModel: TrickMainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        trickMainViewModel.getTrickData(
            tokenViewModel.token.value.toString(),
            args.sportId,
            args.categoryId,
            args.trickId
        )
        return inflater.inflate(R.layout.fragment_trick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trickName = view.findViewById<TextView>(R.id.trick_name_text)
        val trickDescription = view.findViewById<TextView>(R.id.trick_description)
        val trickVideoView = view.findViewById<VideoView>(R.id.trick_video_view)
        val trickCard = view.findViewById<MaterialCardView>(R.id.trick_card_detailed)
        val trickPrerequisitesRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_prerequisites)
        trickPrerequisitesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val trickVariantsRecyclerView = view.findViewById<RecyclerView>(R.id.variants_recyclerView)
        trickVariantsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val statusButton = view.findViewById<Button>(R.id.progress_status_button)

        statusButton.setOnClickListener {
            trickMainViewModel.changeTrickStatus(tokenViewModel.token.value.toString(), args.sportId, args.categoryId, args.trickId)
        }

//        val nextTrickButton = view.findViewById<Button>(R.id.next_trick_button)
//        var nextTrickId: Int = -1;
//
//        nextTrickButton.setOnClickListener {
//            TrickFragmentDirections.actionTrickFragment2Self(nextTrickId, args.sportId, args.categoryId, false)
//        }

        trickMainViewModel.trick.observe(viewLifecycleOwner) { trick ->
            trickName.text = trick.data.name
            trickDescription.text = trick.data.description

            setStatusButtonText(statusButton, trick)
//            setNextTrickId(trick)
            setItemBackgroundColor(trick.data, trickCard)

            trickPrerequisitesRecyclerView.adapter = PrerequisitesAdapter(trick.data.trickParents)

            if (!args.isTrickVariant) {
                trickVariantsRecyclerView.adapter = TrickVaraintAdapter(findNavController(), trick.data.trickVariants, args.sportId, args.categoryId)
            }

            playVideo(trick.data.videoUrl, trickVideoView)
        }
    }

    private fun playVideo(url: String?, view: VideoView) {
        if (url != null) {
            try {
                view.setVideoPath("android.resource://" + requireContext().packageName + "/" + R.raw.test)
                view.setMediaController(MediaController(requireContext()))
                view.requestFocus()
                view.start()
            } catch (e: Exception) {
                println(e)
                view.visibility = View.GONE
            }

        } else {
            view.visibility = View.GONE
        }
    }

    private fun setItemBackgroundColor(trick: TrickExtended, cardView: MaterialCardView) {
        when (trick.status) {
            Status.PLANNING.status -> {
                cardView.setStrokeColor(ContextCompat.getColor(cardView.context, R.color.orange_200))
            }
            Status.STARTED.status -> {
                cardView.setStrokeColor(ContextCompat.getColor(cardView.context, R.color.yellow_200))
            }
            Status.DONE.status -> {
                cardView.setStrokeColor(ContextCompat.getColor(cardView.context, R.color.green_500))
            }
            null -> {
                cardView.setStrokeColor(ContextCompat.getColor(cardView.context, R.color.white))
            }
        }
    }

    private fun setStatusButtonText(statusButton: Button, trick: TrickMainData) {
        if (trick.data.status == null) {
            statusButton.text = "Not Learned"
        } else if (trick.data.status == "Done") {
            statusButton.text = "Done"
        } else if (trick.data.status == "Started") {
            statusButton.text = "Learning"
        }
    }

//    private fun setNextTrickId(trick: TrickMainData): Int {
//        return trick.data.trickChildren.stream()
//            .filter{ child ->
//                child.status != "Done"
//            }.findFirst()
//            .get().trickId
//    }
}