package com.nr.nrsales.repository

import android.graphics.Bitmap
import android.util.Log
import com.nr.nrsales.apis.RemoteDataSource
import com.nr.nrsales.model.BaseApiResponse
import com.nr.nrsales.model.CsvResModel
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.model.UserRes
import com.nr.nrsales.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Objects
import javax.inject.Inject


@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getLogin(Map: HashMap<String, Any>):
            Flow<NetworkResult<UserRes>> {
        return flow {
            emit(safeApiCall { remoteDataSource.loginUser(Map) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getRegistered(body: RequestBody):
            Flow<NetworkResult<RegisterResModel>> {
        return flow {
            emit(safeApiCall { remoteDataSource.registerUser(body) })
        }.flowOn(Dispatchers.IO)
    }
}

