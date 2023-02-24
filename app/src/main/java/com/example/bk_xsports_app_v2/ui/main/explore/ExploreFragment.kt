package com.example.bk_xsports_app_v2.ui.main.explore

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.SportAdapter
import com.example.bk_xsports_app_v2.databinding.FragmentExploreBinding
import com.example.bk_xsports_app_v2.model.SportViewModel

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null

    private val binding get() = _binding!!

    private val sportViewModel: SportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        sportViewModel.getSportData(loadToken())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.explore_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sportViewModel.sport.observe(viewLifecycleOwner) {
            data -> recyclerView.adapter = SportAdapter(findNavController(), data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadToken(): String {
        val sharedPreference = activity?.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sharedPreference?.getString("token", null).toString()
    }
}