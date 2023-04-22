package com.example.bk_xsports_app_v2.ui.main.trick

import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.PrerequisitesAdapter
import com.example.bk_xsports_app_v2.adapters.TrickAdapter
import com.example.bk_xsports_app_v2.adapters.TrickVaraintAdapter
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.model.TrickMainViewModel
import com.example.bk_xsports_app_v2.model.TrickViewModel
import com.example.bk_xsports_app_v2.network.data.TrickExtended
import com.example.bk_xsports_app_v2.util.SpacesItemDecoration
import com.example.bk_xsports_app_v2.util.Status
import com.google.android.material.card.MaterialCardView

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

        trickMainViewModel.trick.observe(viewLifecycleOwner) { trick ->
            trickName.text = trick.data.name
            trickDescription.text = trick.data.description

            playVideo(trick.data.videoUrl, trickVideoView)
            setItemBackgroundColor(trick.data, trickCard)
            trickPrerequisitesRecyclerView.adapter = PrerequisitesAdapter(trick.data.trickParents)

            if (!args.isTrickVariant) {
                trickVariantsRecyclerView.adapter = TrickVaraintAdapter(findNavController(), trick.data.trickVariants, args.sportId, args.categoryId)
            }
        }
    }

    private fun playVideo(url: String?, view: VideoView) {
//        if (url != null) {
//            try {
//                view.setVideoURI(Uri.parse("http://192.168.1.219:8080/api/videos/trick-73.mp4"))
////                trickVideoView.setVideoPath("android.resource://" + requireContext().packageName + "/" + R.raw.test)
//                view.setMediaController(MediaController(requireContext()))
//                view.requestFocus()
//                view.start()
//            } catch (e: Exception) {
//                println(e)
//            }
//
//        }
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
        }
    }
}