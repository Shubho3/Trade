package com.nr.nrsales.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.UserListRes
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject constructor(
    private val repository: Repository, application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<NetworkResult<UserListRes>> = MutableLiveData()
    val response: LiveData<NetworkResult<UserListRes>> = _response
    private val _position: MutableLiveData<NetworkResult<BasicRes>> = MutableLiveData()
    val position: LiveData<NetworkResult<BasicRes>> = _position
    fun fetchget_all_user(body: HashMap<String, Any>) = viewModelScope.launch {
        repository.get_all_user(body).collect { values ->
            _response.value = values
        }
    }
    fun fetch_add_position(body: HashMap<String, Any>) = viewModelScope.launch {
        repository.position(body).collect { values ->
            _position.value = values
        }
    }

}