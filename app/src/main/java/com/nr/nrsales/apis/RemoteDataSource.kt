package com.nr.nrsales.apis

import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(params: HashMap<String, Any>) = apiService.loginUser( params)
    suspend fun registerUser(body: RequestBody) =
        apiService.registerUser( body)

    suspend fun getTypes(params: String) =
        apiService.getCsvList( params)

   suspend fun downloadPDF(params: HashMap<String, Any>) =
        apiService.downloadFile( "867bb48f-ade8-4688-954b-12668ea07977",params)


}