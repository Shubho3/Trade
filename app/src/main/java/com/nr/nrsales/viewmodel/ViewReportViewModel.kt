package com.nr.nrsales.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nr.nrsales.model.DogResponse
import com.nr.nrsales.repository.Repository
import com.nr.nrsales.utils.NetworkResult
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class ViewReportViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<NetworkResult<DogResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<DogResponse>> = _response

    private val _downloadResponse: MutableLiveData<Boolean> = MutableLiveData()
    val downloadResponse = _downloadResponse


}