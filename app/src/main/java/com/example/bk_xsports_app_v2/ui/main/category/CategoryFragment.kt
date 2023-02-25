package com.example.bk_xsports_app_v2.ui.main.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.adapters.CategoryAdapter
import com.example.bk_xsports_app_v2.model.CategoryViewModel
import com.example.bk_xsports_app_v2.model.TokenViewModel

class CategoryFragment : Fragment() {

    private val args: CategoryFragmentArgs by navArgs()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel.getCategoryData(tokenViewModel.token.value.toString(), args.sportId)
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.category_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        categoryViewModel.category.observe(viewLifecycleOwner) {
            data -> recyclerView.adapter = CategoryAdapter(findNavController(), data)
        }
    }
}