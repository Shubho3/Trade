package com.nr.nrsales.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.CsvResModel
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAQuoteViewModel
@Inject constructor(
    private val repository: Repository,
    application: Application) :
    AndroidViewModel(application) {

    private val _response: MutableLiveData<NetworkResult<CsvResModel>> = MutableLiveData()
    val response: LiveData<NetworkResult<CsvResModel>> = _response

}