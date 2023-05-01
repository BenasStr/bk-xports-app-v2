package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.StatisticsData
import kotlinx.coroutines.launch

class StatisticsViewModel: ViewModel() {
    private val _statistics = MutableLiveData<StatisticsData>()
    val statistics: LiveData<StatisticsData> = _statistics

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun getStatisticsData(token: String) {
        println("THis api is called")
        viewModelScope.launch {
            try {
                _statistics.value = Api.retrofitService.getStatistics(token)
                _status.value = true
            } catch (e: Exception) {
                println(e)
                _status.value = false
            }
        }
    }
}