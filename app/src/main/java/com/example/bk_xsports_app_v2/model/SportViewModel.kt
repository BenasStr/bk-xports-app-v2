package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.SportData
import kotlinx.coroutines.launch

class SportViewModel: ViewModel() {
    private val _sport = MutableLiveData<SportData>()
    val sport: LiveData<SportData> = _sport

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun getSportData(token: String) {
        viewModelScope.launch {
            try {
                _sport.value = Api.retrofitService.getSports(token)
                _status.value = true
            } catch (e: Exception) {
                println(e)
                _status.value = false
            }
        }
    }
}