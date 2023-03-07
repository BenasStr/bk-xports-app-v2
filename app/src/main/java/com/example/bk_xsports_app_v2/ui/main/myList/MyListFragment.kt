package com.example.bk_xsports_app_v2.ui.main.myList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.MyListSportAdapter
import com.example.bk_xsports_app_v2.adapters.SportAdapter
import com.example.bk_xsports_app_v2.databinding.FragmentMyListBinding
import com.example.bk_xsports_app_v2.model.SportViewModel
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.util.SpacesItemDecoration

class MyListFragment : Fragment() {

    private var _binding: FragmentMyListBinding? =null

    private val binding get() = _binding!!

    private val sportViewModel: SportViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        sportViewModel.getMyListSportData(tokenViewModel.token.value.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.my_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SpacesItemDecoration(8))

        sportViewModel.sport.observe(viewLifecycleOwner) {
            sport -> recyclerView.adapter = MyListSportAdapter(
                findNavController(),
                sport.data,
                tokenViewModel.token.value.toString(),
                requireContext()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}