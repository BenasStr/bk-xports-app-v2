package com.example.bk_xsports_app_v2.ui.main.trick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.TrickAdapter
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.model.TrickViewModel
import com.example.bk_xsports_app_v2.util.SpacesItemDecoration

class TrickListFragment : Fragment() {

    private val args: TrickListFragmentArgs by navArgs()
    private val trickViewModel: TrickViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trickViewModel.getTricksData(tokenViewModel.token.value.toString(), args.sportId, args.categoryId)
        return inflater.inflate(R.layout.fragment_trick_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "${args.categoryName} Tricks"

        val recyclerView = view.findViewById<RecyclerView>(R.id.trick_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SpacesItemDecoration(8))

        val searchView = view.findViewById<SearchView>(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                trickViewModel.searchTricksData(tokenViewModel.token.value.toString(), args.sportId, args.categoryId, query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        trickViewModel.trick.observe(viewLifecycleOwner) { trick ->
            recyclerView.adapter = TrickAdapter(findNavController(), trick.data, args.sportId, args.categoryId)
        }
    }
}