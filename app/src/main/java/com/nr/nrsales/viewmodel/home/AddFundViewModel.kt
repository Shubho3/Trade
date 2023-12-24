package com.nr.nrsales.viewmodel.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.NotificationRes
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddFundViewModel
@Inject constructor(
    private val repository: Repository, application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<NetworkResult<BasicRes>> = MutableLiveData()
    val response: LiveData<NetworkResult<BasicRes>> = _response
    private val _listResponse: MutableLiveData<NetworkResult<AddFundRes>> = MutableLiveData()
    val listResponse: LiveData<NetworkResult<AddFundRes>> = _listResponse
   private val _notilistResponse: MutableLiveData<NetworkResult<NotificationRes>> = MutableLiveData()
    val notilistResponse: LiveData<NetworkResult<NotificationRes>> = _notilistResponse
   private val _addFundResponse: MutableLiveData<NetworkResult<BasicRes>> = MutableLiveData()
    val addFundResponse: LiveData<NetworkResult<BasicRes>> = _addFundResponse
    fun fetchAddFunds(body: RequestBody) = viewModelScope.launch {
        repository.addFunds(body).collect { values ->
            _response.value = values
        }
    }

    fun fetchGetFund(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.getFund(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _listResponse.value = values
        }
    }
    fun fetchget_add_funds_list_admin(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.get_add_funds_list_admin(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _listResponse.value = values
        }
    }
   fun get_notification(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.get_notification(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _notilistResponse.value = values
        }
    }
    fun add_fund_accept_reject(params: HashMap<String, Any>) = viewModelScope.launch {
        repository.add_fund_accept_reject(params).collect { values ->
            Log.e("TAG", "fetchGetUserResponse: $values")
            _addFundResponse.value = values
        }
    }


}
