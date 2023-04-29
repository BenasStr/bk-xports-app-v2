package com.example.bk_xsports_app_v2.ui.main.trick

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

        playVideo(null, trickVideoView)

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
        if (url != null) {
            try {
                view.setVideoURI(Uri.parse("http://192.168.23.195:9000/videos/trick-73.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=5BS4GQT5YCD3Y0KUGSD8%2F20230426%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230426T073017Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiI1QlM0R1FUNVlDRDNZMEtVR1NEOCIsImV4cCI6MTY4MjUzNjAwOCwicGFyZW50Ijoic29tZUtleSJ9.Ymxlo4dCc9p7GRc50GAosai2JqOwMWvtf5Cf0Y9X9AWBDCsRe4X41NPXlsBGkj1w0cO5B36iH4QiUH8bhGEJmA&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=0587255b17b7e9ca120f109391bceb61f7409e98723a6b19f6ddfe7c33919830"))
//                view.setVideoPath("android.resource://" + requireContext().packageName + "/" + R.raw.test)
                view.setMediaController(MediaController(requireContext()))
                view.requestFocus()
                view.start()
            } catch (e: Exception) {
                println(e)
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
        }
    }
}