package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.Trick
import com.example.bk_xsports_app_v2.network.data.TrickMainData
import kotlinx.coroutines.launch

class TrickMainViewModel: ViewModel() {
    private val _trick = MutableLiveData<TrickMainData>()
    val trick: LiveData<TrickMainData> = _trick

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun getTrickData(token: String, sportId: Int, categoryId: Int, trickId: Int) {
        viewModelScope.launch {
            try {
                println("BLET")
                _trick.value = Api.retrofitService.getTrick(token, sportId, categoryId, trickId)
                println("IAUSD")
            } catch (e: Exception) {
                println("ERRORAS nahui")
                _status.value = false
            }
        }
    }
}