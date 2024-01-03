package com.nr.nrsales.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.PositionRes
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PositionViewModel
@Inject constructor(
    private val repository: Repository, application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<NetworkResult<PositionRes>> = MutableLiveData()
    val response: LiveData<NetworkResult<PositionRes>> = this._response
    private val _addres: MutableLiveData<NetworkResult<BasicRes>> = MutableLiveData()
    val addres: LiveData<NetworkResult<BasicRes>> = this._addres
    fun fetch_position_list(body: HashMap<String, Any>) = viewModelScope.launch {
        repository.position_list(body).collect { values ->
            _response.value = values
        }
    }

    fun fetch_add_position(body: HashMap<String, Any>) = viewModelScope.launch {
        repository.position(body).collect { values ->
            _addres.value = values
        }
    }


}