package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TokenViewModel: ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun addToken(userToken: String) {
        _token.value = userToken
    }
}