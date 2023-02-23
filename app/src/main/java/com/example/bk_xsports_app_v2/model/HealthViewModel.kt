package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import kotlinx.coroutines.launch

class HealthViewModel : ViewModel() {
    private val _health = MutableLiveData<String>()

    val health: LiveData<String> = _health

    init {
        getApiHealthData()
    }

    private fun getApiHealthData() {
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.getHealth()
                _health.value = "Success: ${result.health}"
            } catch (e: Exception) {
                _health.value = "Failure: ${e.message}"
            }
        }
    }
}