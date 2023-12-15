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

    fun fetchDogResponse() = viewModelScope.launch {
        /* repository.getDog().collect { values ->
             _response.value = values
         }*/
    }


    fun downloadpdfFiles( dir: File, fileName: String) {

        viewModelScope.launch {
            repository.downloadPdf( dir, fileName).collect { value ->
                _downloadResponse.value = value
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}