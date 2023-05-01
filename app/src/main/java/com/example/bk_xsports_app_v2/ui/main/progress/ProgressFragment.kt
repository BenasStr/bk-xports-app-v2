package com.example.bk_xsports_app_v2.ui.main.progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.databinding.FragmentMyListBinding
import com.example.bk_xsports_app_v2.databinding.FragmentProgressBinding
import com.example.bk_xsports_app_v2.model.StatisticsViewModel
import com.example.bk_xsports_app_v2.model.TokenViewModel
import com.example.bk_xsports_app_v2.network.data.StatisticsData
import com.example.bk_xsports_app_v2.ui.main.trick.TrickListFragmentDirections

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null

    private val binding get() = _binding!!

    private val statisticsViewModel: StatisticsViewModel by activityViewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        statisticsViewModel.getStatisticsData(tokenViewModel.token.value.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trickLearnedCount = view.findViewById<TextView>(R.id.learned_count_number)
        val trickLearningCount = view.findViewById<TextView>(R.id.learning_count_number)

        val scrollView = view.findViewById<ScrollView>(R.id.sports_progress_scroll)
        val linearLayout = view.findViewById<LinearLayout>(R.id.container_layout)

        val inflater = LayoutInflater.from(requireContext())

        statisticsViewModel.statistics.observe(viewLifecycleOwner) {stats ->
            linearLayout.removeAllViews()

            trickLearnedCount.text = stats.data.learnedCount.toString()
            trickLearningCount.text = stats.data.learningCount.toString()

            for (stat in stats.data.sports) {
                val itemLayout = inflater.inflate(R.layout.progress_sport_card, scrollView, false)
                val sportName = itemLayout.findViewById<TextView>(R.id.sport_name_text)
                sportName.text = stat.name
                val progressBar = itemLayout.findViewById<ProgressBar>(R.id.progressBar)
                progressBar.max = stat.tricksCount
                progressBar.progress = stat.learnedCount
                progressBar.secondaryProgress = stat.learningCount + stat.learnedCount

                val marker = itemLayout.findViewById<TextView>(R.id.learned_count_compare)
                marker.text = "${stat.learnedCount}/${stat.tricksCount}"

                itemLayout.setOnClickListener {
                    val action = ProgressFragmentDirections.actionNavigationProgressToProgressCategoryFragment(stat.name)
                    findNavController().navigate(action)
                }

                linearLayout.addView(itemLayout)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}