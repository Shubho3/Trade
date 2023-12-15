package com.nr.nrsales.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    val _response: MutableLiveData<NetworkResult<RegisterResModel>> = MutableLiveData()

    fun fetchRegisterResponse(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.getRegistered(params).collect { values ->
            _response.value = values
        }
    }
}