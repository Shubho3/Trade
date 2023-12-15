package com.nr.nrsales.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<NetworkResult<String>> = MutableLiveData()
    val response: LiveData<NetworkResult<String>> = _response

    fun fetchLoginResponse(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.getLogin(params).collect { values ->
            _response.value = values } }
}