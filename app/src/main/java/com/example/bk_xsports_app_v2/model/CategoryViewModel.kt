package com.example.bk_xsports_app_v2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bk_xsports_app_v2.network.api.Api
import com.example.bk_xsports_app_v2.network.data.CategoryData
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private val _category = MutableLiveData<CategoryData>()
    val category: LiveData<CategoryData> = _category

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    fun getCategoryData(token: String, sportId: Int) {
        viewModelScope.launch {
            try {
                _category.value = Api.retrofitService.getCategories(token, sportId)
                _status.value = true
            } catch (e: Exception) {
                _status.value = false
            }
        }
    }
}