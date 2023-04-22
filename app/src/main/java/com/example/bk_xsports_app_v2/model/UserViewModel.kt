package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.request.LoginRequest
import com.example.bk_xsports_app_v2.network.request.RegisterRequest
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun login(email: String, password: String) {
        _email.value = email
        _password.value = password

        viewModelScope.launch() {
            try {
                println("AWWW LAWD AHH JEZUS")
                _token.value = "Bearer " + Api.retrofitService.getToken(
                    LoginRequest(email, password)
                ).data.token
                _status.value = true
            } catch (e: Exception) {
                println("ITS EN ERROR")
                println(e)
                _status.value = false
            }
        }
    }

    fun register(name: String, surname: String, username: String, email: String, password: String) {
        _email.value = email
        _password.value = password

        viewModelScope.launch {
            try {
                _token.value = "Bearer " + Api.retrofitService.registerUser(
                    RegisterRequest(
                        username,
                        name,
                        surname,
                        email,
                        password
                    )
                )
                _status.value = true
            } catch (e: Exception) {
                _status.value = false
            }
        }
    }
}