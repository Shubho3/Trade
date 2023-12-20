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
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    val updateUser: MutableLiveData<NetworkResult<RegisterResModel>> = MutableLiveData()
    val getUser: MutableLiveData<NetworkResult<RegisterResModel>> = MutableLiveData()

    fun fetchupdateUserResponse(body: RequestBody) = viewModelScope.launch {
        repository.getRegistered(body).collect { values ->
            updateUser.value = values
        }
    }fun fetchGetUserResponse(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.get_profile(params).collect { values ->
            getUser.value = values
        }
    }
}
