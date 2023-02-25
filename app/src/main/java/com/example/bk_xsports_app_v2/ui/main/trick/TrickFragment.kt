package com.example.bk_xsports_app_v2.ui.main.trick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.TrickAdapter
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.model.TrickViewModel

class TrickFragment : Fragment() {

    private val args: TrickFragmentArgs by navArgs()
    private val trickViewModel: TrickViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trickViewModel.getTricksData(tokenViewModel.token.value.toString(), args.sportId, args.categoryId)
        return inflater.inflate(R.layout.fragment_trick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.trick_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        trickViewModel.trick.observe(viewLifecycleOwner) {
            data -> recyclerView.adapter = TrickAdapter(data)
        }
    }
}