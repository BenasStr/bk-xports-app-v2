package com.example.bk_xsports_app_v2.ui.main.progress

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.databinding.FragmentProgressCategoryBinding
import com.example.bk_xsports_app_v2.model.StatisticsViewModel
import com.example.bk_xsports_app_v2.network.data.SportStatistics
import com.example.bk_xsports_app_v2.network.data.TimeStamps
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_progress_category.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.streams.toList

class ProgressCategoryFragment : Fragment() {

    private val args: ProgressCategoryFragmentArgs by navArgs()
    private var _binding: FragmentProgressCategoryBinding? = null
    private val binding get() = _binding!!

    private val statisticsViewModel: StatisticsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Progress ${args.sportName}"

        val trickLearnedCount = view.findViewById<TextView>(R.id.learned_in_category_count_number)
        val trickLearningCount = view.findViewById<TextView>(R.id.learning_in_category_count_number)

        val scrollView = view.findViewById<ScrollView>(R.id.categories_progress_scroll)
        val linearLayout = view.findViewById<LinearLayout>(R.id.category_scroll_linear_layout)
        val inflater = LayoutInflater.from(requireContext())

        val chartView = view.findViewById<LineChart>(R.id.chart)

        statisticsViewModel.statistics.observe(viewLifecycleOwner) { stats ->
            val stat = stats.data.sports.stream().filter { s -> s.name == args.sportName }
                .findFirst()

            trickLearnedCount.text = stat.get().learnedCount.toString()
            trickLearningCount.text = stat.get().learningCount.toString()

            setUpCategoriesProgressBars(inflater, scrollView, linearLayout, stat.get())
            setUpChart(chartView, stat.get().timeStamps)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpCategoriesProgressBars(inflater: LayoutInflater, scrollView: ScrollView, linearLayout: LinearLayout, sport: SportStatistics) {
        linearLayout.removeAllViews()
        for (stat in sport.sportCategories) {
            val itemLayout = inflater.inflate(R.layout.progress_sport_card, scrollView, false)
            val sportName = itemLayout.findViewById<TextView>(R.id.sport_name_text)
            sportName.text = stat.name

            val progressBar = itemLayout.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.max = stat.tricksCount
            progressBar.progress = stat.learnedCount
            progressBar.secondaryProgress = stat.learningCount + stat.learnedCount

            val marker = itemLayout.findViewById<TextView>(R.id.learned_count_compare)
            marker.text = "${stat.learnedCount}/${stat.tricksCount}"

            linearLayout.addView(itemLayout)
        }
    }

    private fun setUpChart(chartView: LineChart, timeStamps: List<TimeStamps>) {
//        val dataSet = LineDataSet(convertToEntries(timeStamps), "Progress")
//
//        dataSet.setDrawCircles(false)
//        dataSet.lineWidth = 2f
//        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//        dataSet.fillColor = ContextCompat.getColor(requireContext(), R.color.blue_transparent)
//        dataSet.setDrawFilled(true)
//
//        chartView.description.isEnabled = false
//        chartView.legend.isEnabled = false
//        chartView.data = LineData(dataSet)
//
//        chartView.isDragEnabled = false
//        chartView.setScaleEnabled(false)
//        chartView.setPinchZoom(false)
//        chartView.setTouchEnabled(false)
//
//        chartView.axisLeft.setDrawGridLines(false)
//        chartView.axisRight.setDrawGridLines(false)
//        chartView.xAxis.setDrawGridLines(false)
//
////        setAxis(chartView)
//
//        chartView.isHighlightPerTapEnabled = false
//        chartView.invalidate()
    }

    private fun convertStringToDate(date: String): Float {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).time.toFloat()
    }

    private fun convertToEntries(stamps: List<TimeStamps>) : List<Entry> {
        return stamps.stream()
            .map { stamp -> Entry(convertStringToDate(stamp.dateLearned), stamp.countLearned.toFloat()) }
            .toList()
    }

    private fun setAxis(chartView: LineChart) {
        val xAxis = chartView.xAxis

        xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                val date = Date(value.toLong())
                return mFormat.format(date)
            }
        }

        val yAxis = chartView.axisLeft

        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return value.toString()
            }
        }
    }

}