package com.nr.nrsales.viewmodel.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.OutFundResAdmin
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class OutFundViewModel
@Inject constructor(
    private val repository: Repository, application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<NetworkResult<BasicRes>> = MutableLiveData()
    val response: LiveData<NetworkResult<BasicRes>> = _response
    private val _listResponse: MutableLiveData<NetworkResult<OutFundRes>> = MutableLiveData()
    val listResponse: LiveData<NetworkResult<OutFundRes>> = _listResponse
    private val _listResponseadmin: MutableLiveData<NetworkResult<OutFundResAdmin>> = MutableLiveData()
    val listResponseadmin: LiveData<NetworkResult<OutFundResAdmin>> = _listResponseadmin
    fun fetchAddFunds(body: RequestBody) = viewModelScope.launch {
        repository.outFunds(body).collect { values ->
            _response.value = values
        }
    }

    fun fetchout_funds_list(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.out_funds_list(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _listResponse.value = values
        }
    }

    fun fetchget_out_funds_list_admin(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.get_out_funds_list_admin(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _listResponseadmin.value = values
        }
    }


}