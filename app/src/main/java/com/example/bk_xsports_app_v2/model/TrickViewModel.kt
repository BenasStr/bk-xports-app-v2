package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.TrickData
import kotlinx.coroutines.launch

class TrickViewModel: ViewModel() {
    private val _trick = MutableLiveData<TrickData>()
    val trick: LiveData<TrickData> = _trick

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun getTricksData(token: String, sportId: Int, categoryId: Int) {
        viewModelScope.launch {
            try {
                println("VYKSTA UGA UGA")
                _trick.value = Api.retrofitService.getTricks(token, sportId, categoryId)
                _status.value = true
            } catch (e: Exception) {
                println("NEBE")
                println("spor ${sportId}")
                println("categ ${categoryId}")
                println(e)
                _status.value = false
            }
        }
    }
}