package com.example.bk_xsports_app_v2.ui.main.myList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.MyListCategoryAdapter
import com.example.bk_xsports_app_v2.databinding.FragmentMyListCategoryBinding
import com.example.bk_xsports_app_v2.model.CategoryViewModel
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.util.SpacesItemDecoration

class MyListCategoryFragment : Fragment() {

    private var _binding: FragmentMyListCategoryBinding? =null

    private val binding get() = _binding!!

    private val args: MyListCategoryFragmentArgs by navArgs()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListCategoryBinding.inflate(inflater, container, false)
        categoryViewModel.getCategoryData(tokenViewModel.token.value.toString(), args.sportId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "${args.sportName} Categories"

        val recyclerView = view.findViewById<RecyclerView>(R.id.my_list_category_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SpacesItemDecoration(8))

        categoryViewModel.category.observe(viewLifecycleOwner) {
                data -> recyclerView.adapter = MyListCategoryAdapter(
                findNavController(),
                data,
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